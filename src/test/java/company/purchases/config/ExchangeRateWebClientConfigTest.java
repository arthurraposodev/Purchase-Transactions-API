package company.purchases.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateWebClientConfigTest {
    @InjectMocks
    private ExchangeRateWebClientConfig config;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("WebClient bean should not be null")
    void webClientBeanShouldNotBeNull() {
        WebClient webClient = config.webClient();
        assertNotNull(webClient, "WebClient bean should not be null");
    }
}