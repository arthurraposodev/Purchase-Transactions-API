package company.purchases.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class ExchangeRateResponse {
    List<ExchangeRate> data;
}