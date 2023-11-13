package company.purchases.job;

import company.purchases.service.ExchangeRateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ExchangeRateJobTest {

    @Test
    @DisplayName("When the updateRates method is called, it should call the updateExchangeRates method of the ExchangeRateService")
    void updateRatesCallsUpdateExchangeRatesTest() {
        // Arrange
        ExchangeRateService mockExchangeRateService = mock(ExchangeRateService.class);
        ExchangeRateJob exchangeRateJob = new ExchangeRateJob(mockExchangeRateService);

        // Act
        exchangeRateJob.updateRates();

        // Assert
        verify(mockExchangeRateService).updateExchangeRates();
    }

    @Test
    @DisplayName("The updateRates method should be executed every 5 seconds")
    void updateRatesExecutedEvery5SecondsTest() {
        // Arrange
        ExchangeRateService mockExchangeRateService = mock(ExchangeRateService.class);
        ExchangeRateJob exchangeRateJob = new ExchangeRateJob(mockExchangeRateService);

        // Act
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(exchangeRateJob::updateRates, 0, 1, TimeUnit.SECONDS);
        try {
            Thread.sleep(5000); // Wait for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

        // Assert
        verify(mockExchangeRateService, atLeast(5)).updateExchangeRates();
    }

    @Test
    @DisplayName("The ExchangeRateJob class should be annotated with @Component")
    void exchangeRateJobAnnotatedWithComponentTest() {
        // Arrange
        Class<?> exchangeRateJobClass = ExchangeRateJob.class;

        // Act
        Component componentAnnotation = exchangeRateJobClass.getAnnotation(Component.class);

        // Assert
        assertNotNull(componentAnnotation);
    }

    @Test
    @DisplayName("If the ExchangeRateService is null, the updateRates method should throw a NullPointerException")
    void updateRatesThrowsNullPointerExceptionWhenExchangeRateServiceIsNullTest() {
        // Arrange
        ExchangeRateService exchangeRateService = null;
        ExchangeRateJob exchangeRateJob = new ExchangeRateJob(exchangeRateService);

        // Act & Assert
        assertThrows(NullPointerException.class, exchangeRateJob::updateRates);
    }

}