package company.purchases.repository;

import company.purchases.domain.Transaction;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for Transaction repository.
 */
@Repository
public interface TransactionRepository extends ReactiveCrudRepository<Transaction, Long> {
}
