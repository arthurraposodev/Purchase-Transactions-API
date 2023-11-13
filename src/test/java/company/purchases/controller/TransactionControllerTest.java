package company.purchases.controller;

import company.purchases.domain.dto.ConvertedOutputTransactionDTO;
import company.purchases.domain.dto.OutputTransactionDTO;
import company.purchases.service.TransactionService;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TransactionControllerTest {

    @Test
    @DisplayName("The 'getTransactions' method should return all transactions in the database.")
    void testGetAllTransactionsSuccessTest() {
        // Arrange
        OutputTransactionDTO outputTransactionDTO1 = new OutputTransactionDTO();
        outputTransactionDTO1.setId(1L);
        outputTransactionDTO1.setDescription("Test Description 1");
        outputTransactionDTO1.setAmount(BigDecimal.valueOf(100));
        outputTransactionDTO1.setRecordDate(LocalDate.now());

        OutputTransactionDTO outputTransactionDTO2 = new OutputTransactionDTO();
        outputTransactionDTO2.setId(2L);
        outputTransactionDTO2.setDescription("Test Description 2");
        outputTransactionDTO2.setAmount(BigDecimal.valueOf(200));
        outputTransactionDTO2.setRecordDate(LocalDate.now());

        Flux<OutputTransactionDTO> outputTransactionDTOFlux = Flux.just(outputTransactionDTO1, outputTransactionDTO2);

        TransactionService transactionService = mock(TransactionService.class);
        when(transactionService.findAll()).thenReturn(outputTransactionDTOFlux);

        RateLimiter rateLimiter = mock(RateLimiter.class);

        TransactionController transactionController = new TransactionController(transactionService,rateLimiter);

        // Act
        Flux<OutputTransactionDTO> result = transactionController.getTransactions();

        // Assert
        StepVerifier.create(result)
                .expectNext(outputTransactionDTO1, outputTransactionDTO2)
                .verifyComplete();
    }

    @Test
    @DisplayName("The 'getTransactionByIdAndCurrency' method should return a transaction by its ID and currency if provided, or return a transaction by its ID if currency is not provided.")
    void testGetTransactionByIdAndCurrencySuccessTest() {
        // Arrange
        Long id = 1L;
        String currency = "USD";

        OutputTransactionDTO outputTransactionDTO = new OutputTransactionDTO();
        outputTransactionDTO.setId(id);
        outputTransactionDTO.setDescription("Test Description");
        outputTransactionDTO.setAmount(BigDecimal.valueOf(100));
        outputTransactionDTO.setRecordDate(LocalDate.now());

        Mono<ConvertedOutputTransactionDTO> convertedOutputTransactionDTOMono = Mono.just(new ConvertedOutputTransactionDTO());
        Mono<OutputTransactionDTO> outputTransactionDTOMono = Mono.just(new OutputTransactionDTO());

        TransactionService transactionService = mock(TransactionService.class);
        when(transactionService.getTransactionWithConvertedAmount(id, currency)).thenReturn(convertedOutputTransactionDTOMono);
        when(transactionService.getTransactionById(id)).thenReturn(outputTransactionDTOMono);

        RateLimiter rateLimiter = mock(RateLimiter.class);

        TransactionController transactionController = new TransactionController(transactionService,rateLimiter);

        // Act
        Mono<ResponseEntity<ConvertedOutputTransactionDTO>> result = transactionController.getTransactionByIdAndCurrency(id, currency);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(responseEntity -> responseEntity.getStatusCode().equals(HttpStatus.OK))
                .verifyComplete();
    }
}