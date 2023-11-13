package company.purchases.health;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ExchangeRateClientHealth implements ReactiveHealthIndicator {
    private final WebClient webClient;

    @Override
    public Mono<Health> health() {
        String apiUrl = "/services/api/fiscal_service/v1/accounting/od/rates_of_exchange";
        return webClient.get().uri(uriBuilder -> uriBuilder.path(apiUrl)
                        .queryParam("page[size]", 1)
                        .build()).exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return Mono.just(Health.up().build());
            } else {
                return Mono.just(Health.down().withDetail("status", response.statusCode()).build());
            }
        });
    }
}