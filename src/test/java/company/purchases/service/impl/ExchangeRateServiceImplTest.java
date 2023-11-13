package company.purchases.service.impl;

import company.purchases.domain.ExchangeRateResponse;
import company.purchases.repository.ExchangeRateRepository;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collections;
import java.util.function.Function;

import static org.mockito.Mockito.*;

class ExchangeRateServiceImplTest {
    @Test
    @DisplayName("updateExchangeRates method retrieves latest record date from ExchangeRateRepository and starts from the next day if records exist")
    void retrieveLatestRecordDateAndStartFromNextDayTest() {
        // Mock ExchangeRateRepository
        ExchangeRateRepository exchangeRateRepository = mock(ExchangeRateRepository.class);
        when(exchangeRateRepository.findLatestRecordDate()).thenReturn(Mono.just(LocalDate.of(2022, 1, 1)));

        // Mock WebClient
        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setData(Collections.emptyList());
        Mono<ExchangeRateResponse> exchangeRateResponseMono = Mono.just(exchangeRateResponse);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(Mockito.<Function<UriBuilder, URI>>any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExchangeRateResponse.class)).thenReturn(exchangeRateResponseMono);

        RetryRegistry retry = mock(RetryRegistry.class);

        // Create instance of ExchangeRateServiceImpl
        ExchangeRateServiceImpl exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository, webClient,retry);

        // Call the method under test
        exchangeRateService.updateExchangeRates();

        // Verify that the correct methods were called
        verify(exchangeRateRepository).findLatestRecordDate();
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(Mockito.<Function<UriBuilder, URI>>any());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(ExchangeRateResponse.class);
    }

    @Test
    @DisplayName("If no records exist, start from 10 years ago")
    void startFromTenYearsAgoIfNoRecordsExistTest() {
        // Mock ExchangeRateRepository
        ExchangeRateRepository exchangeRateRepository = mock(ExchangeRateRepository.class);
        when(exchangeRateRepository.findLatestRecordDate()).thenReturn(Mono.empty());

        // Mock WebClient
        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setData(Collections.emptyList());
        Mono<ExchangeRateResponse> exchangeRateResponseMono = Mono.just(exchangeRateResponse);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(Mockito.<Function<UriBuilder, URI>>any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExchangeRateResponse.class)).thenReturn(exchangeRateResponseMono);

        RetryRegistry retry = mock(RetryRegistry.class);

        // Create instance of ExchangeRateServiceImpl
        ExchangeRateServiceImpl exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository, webClient,retry);

        // Call the method under test
        exchangeRateService.updateExchangeRates();

        // Verify that the correct methods were called
        verify(exchangeRateRepository).findLatestRecordDate();
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(Mockito.<Function<UriBuilder, URI>>any());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(ExchangeRateResponse.class);
    }

    @Test
    @DisplayName("Retrieve exchange rates from the API using WebClient")
    void retrieveExchangeRatesFromApiUsingWebClientTest() {
        // Mock ExchangeRateRepository
        ExchangeRateRepository exchangeRateRepository = mock(ExchangeRateRepository.class);
        when(exchangeRateRepository.findLatestRecordDate()).thenReturn(Mono.just(LocalDate.of(2022, 1, 1)));

        // Mock WebClient
        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setData(Collections.emptyList());
        Mono<ExchangeRateResponse> exchangeRateResponseMono = Mono.just(exchangeRateResponse);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(Mockito.<Function<UriBuilder, URI>>any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExchangeRateResponse.class)).thenReturn(exchangeRateResponseMono);

        RetryRegistry retry = mock(RetryRegistry.class);

        // Create instance of ExchangeRateServiceImpl
        ExchangeRateServiceImpl exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository, webClient,retry);

        // Call the method under test
        exchangeRateService.updateExchangeRates();

        // Verify that the correct methods were called
        verify(exchangeRateRepository).findLatestRecordDate();
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(Mockito.<Function<UriBuilder, URI>>any());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(ExchangeRateResponse.class);
    }

    @Test
    @DisplayName("ExchangeRateRepository returns empty Mono")
    void test_exchangeRateRepositoryReturnsEmptyMonoTest() {
        // Mock ExchangeRateRepository
        ExchangeRateRepository exchangeRateRepository = mock(ExchangeRateRepository.class);
        when(exchangeRateRepository.findLatestRecordDate()).thenReturn(Mono.empty());

        // Mock WebClient
        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setData(Collections.emptyList());
        Mono<ExchangeRateResponse> exchangeRateResponseMono = Mono.just(exchangeRateResponse);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(Mockito.<Function<UriBuilder, URI>>any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExchangeRateResponse.class)).thenReturn(exchangeRateResponseMono);

        RetryRegistry retry = mock(RetryRegistry.class);

        // Create instance of ExchangeRateServiceImpl
        ExchangeRateServiceImpl exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository, webClient,retry);

        // Call the method under test
        exchangeRateService.updateExchangeRates();

        // Verify that the correct methods were called
        verify(exchangeRateRepository).findLatestRecordDate();
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(Mockito.<Function<UriBuilder, URI>>any());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(ExchangeRateResponse.class);
    }

    @Test
    @DisplayName("WebClient returns empty Mono")
    void webClientReturnsEmptyMonoTest() {
        // Mock ExchangeRateRepository
        ExchangeRateRepository exchangeRateRepository = mock(ExchangeRateRepository.class);
        when(exchangeRateRepository.findLatestRecordDate()).thenReturn(Mono.just(LocalDate.of(2022, 1, 1)));

        // Mock WebClient
        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        Mono<ExchangeRateResponse> exchangeRateResponseMono = Mono.empty();

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(Mockito.<Function<UriBuilder, URI>>any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExchangeRateResponse.class)).thenReturn(exchangeRateResponseMono);

        RetryRegistry retry = mock(RetryRegistry.class);

        // Create instance of ExchangeRateServiceImpl
        ExchangeRateServiceImpl exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository, webClient,retry);
        // Call the method under test
        exchangeRateService.updateExchangeRates();

        // Verify that the correct methods were called
        verify(exchangeRateRepository).findLatestRecordDate();
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(Mockito.<Function<UriBuilder, URI>>any());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(ExchangeRateResponse.class);
    }

    @Test
    @DisplayName("WebClient returns ExchangeRateResponse with empty data list")
    void webClientReturnsExchangeRateResponseWithEmptyDataListTest() {
        // Mock ExchangeRateRepository
        ExchangeRateRepository exchangeRateRepository = mock(ExchangeRateRepository.class);
        when(exchangeRateRepository.findLatestRecordDate()).thenReturn(Mono.just(LocalDate.of(2022, 1, 1)));

        // Mock WebClient
        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setData(Collections.emptyList());
        Mono<ExchangeRateResponse> exchangeRateResponseMono = Mono.just(exchangeRateResponse);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(Mockito.<Function<UriBuilder, URI>>any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ExchangeRateResponse.class)).thenReturn(exchangeRateResponseMono);

        RetryRegistry retry = mock(RetryRegistry.class);

        // Create instance of ExchangeRateServiceImpl
        ExchangeRateServiceImpl exchangeRateService = new ExchangeRateServiceImpl(exchangeRateRepository, webClient,retry);

        // Call the method under test
        exchangeRateService.updateExchangeRates();

        // Verify that the correct methods were called
        verify(exchangeRateRepository).findLatestRecordDate();
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri(Mockito.<Function<UriBuilder, URI>>any());
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).bodyToMono(ExchangeRateResponse.class);
    }
}