package company.purchases.service;

import company.purchases.domain.dto.ConvertedOutputTransactionDTO;
import company.purchases.domain.dto.OutputTransactionDTO;
import company.purchases.domain.dto.InputTransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The interface Transaction service.
 */
public interface TransactionService {
    /**
     * Gets transaction with converted amount in the targetCurrency.
     *
     * @param transactionId  the transaction id
     * @param targetCurrency the target currency
     * @return the transaction with converted amount
     */
    Mono<ConvertedOutputTransactionDTO> getTransactionWithConvertedAmount(Long transactionId, String targetCurrency);

    /**
     * Gets transaction by id.
     *
     * @param transactionId the transaction id
     * @return the transaction by id
     */
    Mono<OutputTransactionDTO> getTransactionById(Long transactionId);

    /**
     * Save transaction.
     *
     * @param inputTransactionDTO the input transaction dto
     * @return the mono
     */
    Mono<OutputTransactionDTO> save(InputTransactionDTO inputTransactionDTO);

    /**
     * Find all Transactions.
     *
     * @return the flux
     */
    Flux<OutputTransactionDTO> findAll();
}
