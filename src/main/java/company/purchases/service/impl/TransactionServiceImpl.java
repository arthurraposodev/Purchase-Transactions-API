package company.purchases.service.impl;

import company.purchases.domain.Transaction;
import company.purchases.domain.dto.ConvertedOutputTransactionDTO;
import company.purchases.domain.dto.InputTransactionDTO;
import company.purchases.domain.dto.OutputTransactionDTO;
import company.purchases.repository.ExchangeRateRepository;
import company.purchases.domain.mapper.TransactionMapper;
import company.purchases.repository.TransactionRepository;
import company.purchases.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * Transaction service implementation.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public Mono<ConvertedOutputTransactionDTO> getTransactionWithConvertedAmount(Long transactionId, String targetCurrency) {
        log.info("Fetching transaction with ID {} and converting to currency {}", transactionId, targetCurrency);
        return transactionRepository.findById(transactionId)
                .flatMap(transaction -> {
                    LocalDate recordDate = transaction.getRecordDate();
                    LocalDate sixMonthsAgo = recordDate.minusMonths(6);
                    return exchangeRateRepository.findByCurrencyAndRecordDateMinusSixMonths(targetCurrency, sixMonthsAgo, recordDate)
                            .map(exchangeRate -> {
                                BigDecimal convertedAmount = transaction.getAmount().multiply(exchangeRate.getRate()).setScale(2, RoundingMode.HALF_EVEN);
                                return transactionMapper.convertToConvertedOutputTransactionDTO(transaction,exchangeRate.getRate(),convertedAmount);
                            })
                            .doOnSuccess(item -> log.debug("Successfully converted transaction amount"))
                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "No exchange rate data available for the past six months for the given currency.")))
                            .doOnError(e -> log.error("Error in getting transaction with converted amount", e));
                })
                .doOnError(e -> log.error("Error fetching transaction by ID {}", transactionId, e));
    }

    public Mono<OutputTransactionDTO> getTransactionById(Long transactionId) {
        log.info("Fetching transaction by ID {}", transactionId);
        return transactionRepository.findById(transactionId)
                .map(transactionMapper::convertToOutputTransactionDTO)
                .doOnSuccess(item -> log.debug("Successfully fetched transaction"))
                .doOnError(e -> log.error("Error fetching transaction by ID {}", transactionId, e));
    }

    public Mono<OutputTransactionDTO> save(InputTransactionDTO inputTransactionDTO) {
        Transaction transaction = transactionMapper.convertToEntity(inputTransactionDTO);
        BigDecimal roundedAmount = transaction.getAmount().setScale(2, RoundingMode.HALF_EVEN);
        transaction.setAmount(roundedAmount);
        log.info("Saving transaction");
        return transactionRepository.save(transaction)
                .map(transactionMapper::convertToOutputTransactionDTO)
                .doOnSuccess(item -> log.debug("Successfully saved transaction"))
                .doOnError(e -> log.error("Error saving transaction", e));
    }

    public Flux<OutputTransactionDTO> findAll() {
        log.info("Fetching all transactions");
        return transactionRepository.findAll()
                .map(transactionMapper::convertToOutputTransactionDTO)
                .doOnNext(item -> log.debug("Fetched transaction"))
                .doOnError(e -> log.error("Error fetching transactions", e));
    }
}