package company.purchases.service.impl;

import company.purchases.domain.ExchangeRateResponse;
import company.purchases.repository.ExchangeRateRepository;
import company.purchases.service.ExchangeRateService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final WebClient webClient;
    private final Retry retry;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository, WebClient webClient, RetryRegistry retryRegistry) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.webClient = webClient;
        this.retry = retryRegistry.retry("exchangeRateRetry");
    }

    public void updateExchangeRates() {
        log.info("Starting exchange rate update process");
        exchangeRateRepository.findLatestRecordDate()
                .map(latestDate -> latestDate.plusDays(1))
                .defaultIfEmpty(LocalDate.now().minusYears(10))
                .flatMapMany(startDate -> {
                    String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String apiUrl = "/services/api/fiscal_service/v1/accounting/od/rates_of_exchange";
                    return webClient.get()
                            .uri(uriBuilder -> uriBuilder.path(apiUrl)
                                    .queryParam("page[size]", 10000)
                                    .queryParam("filter", "record_date:gte:" + formattedStartDate)
                                    .queryParam("fields", "record_date,currency,exchange_rate")
                                    .build())
                            .retrieve()
                            .bodyToMono(ExchangeRateResponse.class)
                            .transformDeferred(io.github.resilience4j.reactor.retry.RetryOperator.of(retry));
                })
                .flatMap(response -> Flux.fromIterable(response.getData()))  // Processing each response item
                .flatMap(exchangeRateRepository::save)  // Saving each exchange rate record
                .doOnNext(item -> log.debug("Successfully saved exchange rate record"))
                .doOnError(e -> log.error("Error occurred during exchange rate update process", e))
                .onErrorResume(e -> Flux.empty())  // Continue processing even if an error occurs
                .subscribe(
                        null,  // onNext is handled above
                        error -> log.error("Error occurred during the update process", error),
                        () -> log.info("Exchange rate update process completed")
                );
    }
}