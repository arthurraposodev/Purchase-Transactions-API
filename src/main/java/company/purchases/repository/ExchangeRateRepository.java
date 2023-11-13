package company.purchases.repository;

import company.purchases.domain.ExchangeRate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

/**
 * Interface for the Exchange rate repository.
 */
@Repository
public interface ExchangeRateRepository extends ReactiveCrudRepository<ExchangeRate, Long> {
    /**
     * Find latest record date mono.
     *
     * @return the mono
     */
    @Query("SELECT record_date FROM exchange_rates ORDER BY record_date DESC LIMIT 1")
    Mono<LocalDate> findLatestRecordDate();

    /**
     * Find exchange rate for the given exchange_rate in the last 6 months of given record_date.
     *
     * @param currency     the currency
     * @param sixMonthsAgo the six months ago
     * @param recordDate   the record date
     * @return the mono
     */
    @Query("SELECT * FROM exchange_rates WHERE currency = :currency AND record_date BETWEEN :sixMonthsAgo AND :recordDate ORDER BY record_date DESC LIMIT 1")
    Mono<ExchangeRate> findByCurrencyAndRecordDateMinusSixMonths(String currency, LocalDate sixMonthsAgo, LocalDate recordDate);
}
