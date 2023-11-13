package company.purchases.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateResponseTest {

    @Test
    @DisplayName("ExchangeRateResponse object is created successfully with a list of ExchangeRate objects")
    void test_createExchangeRateResponseWithListTest() {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        exchangeRates.add(new ExchangeRate(1L, "USD", new BigDecimal("1.0"), LocalDate.now()));
        exchangeRates.add(new ExchangeRate(2L, "EUR", new BigDecimal("0.9"), LocalDate.now()));

        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setData(exchangeRates);

        assertNotNull(exchangeRateResponse);
        assertEquals(exchangeRates, exchangeRateResponse.getData());
    }

    @Test
    @DisplayName("The 'data' field of ExchangeRateResponse object can be accessed and modified successfully")
    void test_accessAndModifyDataFieldTest() {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        exchangeRates.add(new ExchangeRate(1L, "USD", new BigDecimal("1.0"), LocalDate.now()));

        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setData(exchangeRates);

        assertNotNull(exchangeRateResponse.getData());
        assertEquals(exchangeRates, exchangeRateResponse.getData());

        List<ExchangeRate> newExchangeRates = new ArrayList<>();
        newExchangeRates.add(new ExchangeRate(2L, "EUR", new BigDecimal("0.9"), LocalDate.now()));

        exchangeRateResponse.setData(newExchangeRates);

        assertNotNull(exchangeRateResponse.getData());
        assertEquals(newExchangeRates, exchangeRateResponse.getData());
    }

    @Test
    @DisplayName("ExchangeRateResponse object can be serialized and deserialized successfully using a JSON serializer")
    void test_serializeAndDeserializeExchangeRateResponseTest() throws JsonProcessingException {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        exchangeRates.add(new ExchangeRate(1L, "USD", new BigDecimal("1.0"), LocalDate.now()));

        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setData(exchangeRates);

        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule())
                .build();
        String json = objectMapper.writeValueAsString(exchangeRateResponse);

        assertNotNull(json);

        ExchangeRateResponse deserializedResponse = objectMapper.readValue(json, ExchangeRateResponse.class);

        assertNotNull(deserializedResponse);
        assertEquals(exchangeRateResponse.getData(), deserializedResponse.getData());
    }

    @Test
    @DisplayName("ExchangeRateResponse object is created successfully with an empty list of ExchangeRate objects")
    void test_createExchangeRateResponseWithEmptyListTest() {
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setData(Collections.emptyList());

        assertNotNull(exchangeRateResponse);
        assertTrue(exchangeRateResponse.getData().isEmpty());
    }

    @Test
    @DisplayName("ExchangeRateResponse object is created successfully with a null list of ExchangeRate objects")
    void test_createExchangeRateResponseWithNullListTest() {
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setData(null);

        assertNotNull(exchangeRateResponse);
        assertNull(exchangeRateResponse.getData());
    }

    @Test
    @DisplayName("ExchangeRateResponse object is created successfully with a list of ExchangeRate objects containing null values")
    void test_createExchangeRateResponseWithListContainingNullValuesTest() {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        exchangeRates.add(null);

        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setData(exchangeRates);

        assertNotNull(exchangeRateResponse);
        assertEquals(exchangeRates, exchangeRateResponse.getData());
    }

}