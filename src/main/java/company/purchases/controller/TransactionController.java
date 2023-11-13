package company.purchases.controller;

import company.purchases.domain.Transaction;
import company.purchases.domain.dto.OutputTransactionDTO;
import company.purchases.domain.dto.InputTransactionDTO;
import company.purchases.domain.mapper.TransactionMapper;
import company.purchases.service.TransactionService;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions")
@Slf4j
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final RateLimiter rateLimiter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction was created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class))})
    })
    @Operation(summary = "Create a new transaction in the Database")
    public Mono<OutputTransactionDTO> saveTransaction(@Valid @RequestBody InputTransactionDTO inputTransactionDTO) {
        log.info("Received request to create transaction");
        return Mono.just(inputTransactionDTO)
                .map(transactionMapper::convertToEntity)
                .flatMap(transactionService::save)
                .transformDeferred(RateLimiterOperator.of(rateLimiter))
                .doOnSuccess(t -> log.debug("Transaction created successfully"))
                .doOnError(e -> log.error("Error creating transaction: ", e))
                .onErrorResume(DataAccessException.class, e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", e)))
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", e)));
    }

    @Operation(summary = "Get all transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found purchase records table",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class))})
    })
    @GetMapping
    public Flux<OutputTransactionDTO> getTransactions() {
        log.info("Received request to get all transactions");
        return transactionService.findAll()
                .transformDeferred(RateLimiterOperator.of(rateLimiter))
                .doOnEach(signal -> {
                    if (signal.hasError()) {
                        log.error("Error getting transactions: ", signal.getThrowable());
                    }
                })
                .onErrorResume(DataAccessException.class, e -> Flux.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", e)))
                .onErrorResume(e -> Flux.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", e)));
    }

    @Operation(summary = "Get transaction by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found record for given Id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class))}),
            @ApiResponse(responseCode = "404", description = "Record not found for given Id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class))}),
            @ApiResponse(responseCode = "400", description = "Request was not well-formed or there is no exchange rate data for given currency in the last six months of the purchase date",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transaction.class))})
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<OutputTransactionDTO>> getTransactionByIdAndCurrency(@PathVariable Long id, @RequestParam(required = false) String currency) {
        log.info("Received request to get transaction by ID: {} and currency: {}", id, currency);
        return (currency == null ?
                transactionService.getTransactionById(id) :
                transactionService.getTransactionWithConvertedAmount(id, currency))
                .map(transactionMapper::convertToOutputTransactionDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .transformDeferred(RateLimiterOperator.of(rateLimiter))
                .doOnError(e -> {
                    if (e instanceof DataAccessException) {
                        log.error("Database error when getting transaction by ID and currency: ", e);
                    } else {
                        log.error("Unexpected error when getting transaction by ID and currency: ", e);
                    }
                })
                .onErrorResume(DataAccessException.class, e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", e)))
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", e)));
    }

}
