package company.purchases.health;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

class ExchangeRateClientHealthTest {

    private MockWebServer mockWebServer;
    private ExchangeRateClientHealth exchangeRateClientHealth;

    @BeforeEach
    void setUp() {
        mockWebServer = new MockWebServer();
        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();
        exchangeRateClientHealth = new ExchangeRateClientHealth(webClient);
    }

    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("Health check should be Up in case of successful API call")
    void healthCheckUpTest() {
        // Simulate a successful response
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("response body")
                .setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE));

        // Test
        StepVerifier.create(exchangeRateClientHealth.health())
                .expectNext(Health.up().build())
                .verifyComplete();
    }

    @Test
    @DisplayName("Health check should be Down in case of unsuccessful API call")
    void healthCheckDownTest() {
        // Simulate an error response
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("error body")
                .setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE));

        // Test
        StepVerifier.create(exchangeRateClientHealth.health())
                .expectNextMatches(health -> health.getStatus().equals(Health.down().build().getStatus()))
                .verifyComplete();
    }
}
