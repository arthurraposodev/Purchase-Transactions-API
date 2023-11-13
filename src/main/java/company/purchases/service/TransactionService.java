package company.purchases.service;

import company.purchases.domain.Transaction;
import company.purchases.domain.dto.OutputTransactionDTO;
import company.purchases.domain.dto.InputTransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Mono<Transaction> getTransactionWithConvertedAmount(Long transactionId, String targetCurrency);
    Mono<Transaction> getTransactionById(Long transactionId);
    Mono<OutputTransactionDTO> save(Transaction transaction);
    Flux<OutputTransactionDTO> findAll();
}
