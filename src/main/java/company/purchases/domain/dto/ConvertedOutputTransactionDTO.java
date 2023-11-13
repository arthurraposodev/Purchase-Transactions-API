package company.purchases.domain.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Converted output transaction dto, with exchange rate and converted amount information.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ConvertedOutputTransactionDTO extends OutputTransactionDTO {
    private BigDecimal exchangeRate;
    private BigDecimal convertedAmount;
}
