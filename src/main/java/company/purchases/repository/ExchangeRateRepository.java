package company.purchases.repository;

import company.purchases.domain.ExchangeRate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface ExchangeRateRepository extends ReactiveCrudRepository<ExchangeRate, Long> {
    @Query("SELECT record_date FROM exchange_rates ORDER BY record_date DESC LIMIT 1")
    Mono<LocalDate> findLatestRecordDate();
    @Query("SELECT * FROM exchange_rates WHERE currency = :currency AND record_date BETWEEN :sixMonthsAgo AND :recordDate ORDER BY record_date DESC LIMIT 1")
    Mono<ExchangeRate> findByCurrencyAndRecordDateMinusSixMonths(String currency, LocalDate recordDate);
}
