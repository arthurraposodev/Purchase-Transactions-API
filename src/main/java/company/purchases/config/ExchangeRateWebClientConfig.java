package company.purchases.config;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ExchangeRateWebClientConfig {

    private static final int BUFFER_SIZE = 1024 * 1024; // 1 MB
    private static final String BASE_URL = "https://api.fiscaldata.treasury.gov";

    @Bean
    public WebClient webClient(){
        return WebClient.builder().codecs(clientCodecConfigurer -> clientCodecConfigurer
                .defaultCodecs()
                .maxInMemorySize(BUFFER_SIZE)).baseUrl(BASE_URL).build();
    }
}
