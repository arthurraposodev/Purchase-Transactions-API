package company.purchases.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Converted output transaction dto, with exchange rate and converted amount information.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ConvertedOutputTransactionDTO extends OutputTransactionDTO {
    private BigDecimal exchangeRate;
    private BigDecimal convertedAmount;
}
