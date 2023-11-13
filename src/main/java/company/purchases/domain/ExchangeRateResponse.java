package company.purchases.domain;

import lombok.Data;

import java.util.List;

/**
 * Exchange rate response.
 */
@Data
public class ExchangeRateResponse {
    /**
     * The exchange rate list wrapper, data.
     */
    List<ExchangeRate> data;
}