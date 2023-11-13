package company.purchases.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration Spring component class to setup a Webclient for interfacing with the Treasury API.
 */
@Component
public class ExchangeRateWebClientConfig {

    private static final int BUFFER_SIZE = 1024 * 1024; // 1 MB
    private static final String BASE_URL = "https://api.fiscaldata.treasury.gov";

    /**
     * Treasury API web client.
     *
     * @return the web client
     */
    @Bean
    public WebClient webClient(){
        return WebClient.builder().codecs(clientCodecConfigurer -> clientCodecConfigurer
                .defaultCodecs()
                .maxInMemorySize(BUFFER_SIZE)).baseUrl(BASE_URL).build();
    }
}
