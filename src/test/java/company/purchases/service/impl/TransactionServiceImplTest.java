package company.purchases.service.impl;

import company.purchases.domain.ExchangeRate;
import company.purchases.domain.Transaction;
import company.purchases.domain.dto.ConvertedOutputTransactionDTO;
import company.purchases.domain.dto.InputTransactionDTO;
import company.purchases.domain.dto.OutputTransactionDTO;
import company.purchases.domain.mapper.TransactionMapper;
import company.purchases.repository.ExchangeRateRepository;
import company.purchases.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionServiceImplTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    ExchangeRateRepository exchangeRateRepository;

    @Mock
    TransactionMapper transactionMapper;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("'getTransactionWithConvertedAmount' returns a Mono of Transaction with converted amount when given a valid transactionId and targetCurrency")
    void getTransactionWithConvertedAmount_validInputTest() {
        // Arrange
        Long transactionId = 1L;
        String targetCurrency = "USD";
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setRecordDate(LocalDate.now());
        LocalDate sixMonthsAgo = transaction.getRecordDate().minusMonths(6);
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setRate(BigDecimal.valueOf(1.5));
        ConvertedOutputTransactionDTO convertedOutputTransactionDTO = new ConvertedOutputTransactionDTO();
        convertedOutputTransactionDTO.setAmount(BigDecimal.valueOf(150));
        when(transactionRepository.findById(transactionId)).thenReturn(Mono.just(transaction));
        when(exchangeRateRepository.findByCurrencyAndRecordDateMinusSixMonths(targetCurrency, sixMonthsAgo, transaction.getRecordDate())).thenReturn(Mono.just(exchangeRate));
        when(transactionMapper.convertToConvertedOutputTransactionDTO(any(),any(),any())).thenReturn(convertedOutputTransactionDTO);

        // Act
        Mono<ConvertedOutputTransactionDTO> result = transactionService.getTransactionWithConvertedAmount(transactionId, targetCurrency);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(t -> t.getAmount().toString().equals("150"))
                .verifyComplete();
    }

    @Test
    @DisplayName("'getTransactionById' returns a Mono of OutputTransactionDTO when given a valid transactionId")
    void getTransactionById_validInputTest() {
        // Arrange
        Long transactionId = 1L;
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        when(transactionRepository.findById(transactionId)).thenReturn(Mono.just(transaction));
        OutputTransactionDTO outputTransactionDTO = new OutputTransactionDTO();
        when(transactionMapper.convertToOutputTransactionDTO(transaction)).thenReturn(outputTransactionDTO);

        // Act
        Mono<OutputTransactionDTO> result = transactionService.getTransactionById(transactionId);

        // Assert
        StepVerifier.create(result)
                .expectNext(outputTransactionDTO)
                .verifyComplete();
    }

    @Test
    @DisplayName("'save' returns a Mono of OutputTransactionDTO when given a valid Transaction")
    void saveValidInputTest() {
        // Arrange
        InputTransactionDTO inputTransactionDTO = new InputTransactionDTO();
        inputTransactionDTO.setAmount(BigDecimal.valueOf(100));
        inputTransactionDTO.setRecordDate(LocalDate.now());

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setRecordDate(LocalDate.now());

        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));
        when(transactionMapper.convertToEntity(any())).thenReturn(transaction);
        when(transactionMapper.convertToOutputTransactionDTO(transaction)).thenReturn(new OutputTransactionDTO());

        // Act
        Mono<OutputTransactionDTO> result = transactionService.save(inputTransactionDTO);

        // Assert
        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("'getTransactionWithConvertedAmount' returns an error Mono when no exchange rate data is available for the past six months for the given currency")
    void getTransactionWithConvertedAmountNoExchangeRateDataTest() {
        // Arrange
        Long transactionId = 1L;
        String targetCurrency = "USD";
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setRecordDate(LocalDate.now());
        LocalDate sixMonthsAgo = transaction.getRecordDate().minusMonths(6);
        when(transactionRepository.findById(transactionId)).thenReturn(Mono.just(transaction));
        when(exchangeRateRepository.findByCurrencyAndRecordDateMinusSixMonths(targetCurrency, sixMonthsAgo, transaction.getRecordDate())).thenReturn(Mono.empty());

        // Act
        Mono<ConvertedOutputTransactionDTO> result = transactionService.getTransactionWithConvertedAmount(transactionId, targetCurrency);

        // Assert
        StepVerifier.create(result)
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("'save' rounds the amount to 2 decimal places when given a Transaction with more than 2 decimal places")
    void saveRoundsAmountToTwoDecimalPlacesTest() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(100.123));
        transaction.setRecordDate(LocalDate.now());
        InputTransactionDTO inputTransactionDTO = new InputTransactionDTO();

        when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));
        when(transactionMapper.convertToEntity(any())).thenReturn(transaction);
        when(transactionMapper.convertToOutputTransactionDTO(transaction)).thenReturn(new OutputTransactionDTO());

        // Act
        Mono<OutputTransactionDTO> result = transactionService.save(inputTransactionDTO);

        // Assert
        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
        assertEquals(BigDecimal.valueOf(100.12), transaction.getAmount());
    }

    @Test
    @DisplayName("'findAll' returns an empty Flux when there are no transactions in the repository")
    void findAllEmptyRepositoryTest() {
        // Arrange
        when(transactionRepository.findAll()).thenReturn(Flux.empty());

        // Act
        Flux<OutputTransactionDTO> result = transactionService.findAll();

        // Assert
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }
}