package company.purchases.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateTest {

    @Test
    @DisplayName("ExchangeRate object can be created with valid input parameters")
    void test_createExchangeRateWithValidInputTest() {
        ExchangeRate exchangeRate = new ExchangeRate(1L, "USD", new BigDecimal("1.5"), LocalDate.now());
        assertNotNull(exchangeRate);
        assertEquals(1L, exchangeRate.getId());
        assertEquals("USD", exchangeRate.getCurrency());
        assertEquals(new BigDecimal("1.5"), exchangeRate.getRate());
        assertEquals(LocalDate.now(), exchangeRate.getRecordDate());
    }

    @Test
    @DisplayName("ExchangeRate object can be updated with valid input parameters")
    void test_updateExchangeRateWithValidInputTest() {
        ExchangeRate exchangeRate = new ExchangeRate(1L, "USD", new BigDecimal("1.5"), LocalDate.now());
        exchangeRate.setCurrency("EUR");
        exchangeRate.setRate(new BigDecimal("1.2"));
        exchangeRate.setRecordDate(LocalDate.now().minusDays(1));
        assertEquals("EUR", exchangeRate.getCurrency());
        assertEquals(new BigDecimal("1.2"), exchangeRate.getRate());
        assertEquals(LocalDate.now().minusDays(1), exchangeRate.getRecordDate());
    }

    @Test
    @DisplayName("ExchangeRate object can be converted to JSON format")
    void test_convertExchangeRateToJsonTest() throws JsonProcessingException {
        ExchangeRate exchangeRate = new ExchangeRate(1L, "USD", new BigDecimal("1.5"), LocalDate.now());
        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule())
                .build();
        String json = objectMapper.writeValueAsString(exchangeRate);
        assertNotNull(json);
        assertTrue(json.contains("\"id\":1"));
        assertTrue(json.contains("\"currency\":\"USD\""));
        assertTrue(json.contains("\"exchange_rate\":1.5"));
    }

    @Test
    @DisplayName("ExchangeRate object cannot be created with null currency")
    void test_createExchangeRateWithNullCurrencyTest() {
        assertThrows(NullPointerException.class, () -> new ExchangeRate(1L, null, new BigDecimal("1.5"), LocalDate.now()));
    }

    @Test
    @DisplayName("ExchangeRate object cannot be created with null exchange rate")
    void test_createExchangeRateWithNullExchangeRateTest() {
        assertThrows(NullPointerException.class, () -> new ExchangeRate(1L, "USD", null, LocalDate.now()));
    }

    @Test
    @DisplayName("ExchangeRate object cannot be created with null record date")
    void test_createExchangeRateWithNullRecordDateTest() {
        assertThrows(NullPointerException.class, () -> new ExchangeRate(1L, "USD", new BigDecimal("1.5"), null));
    }

}