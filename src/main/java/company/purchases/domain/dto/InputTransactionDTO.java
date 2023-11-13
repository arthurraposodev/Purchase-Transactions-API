package company.purchases.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Input transaction dto, for receiving data in endpoints.
 */
@Data
public class InputTransactionDTO {
    @Size(max = 50,message = "Description field can only be 50 characters long.")
    private String description;
    @Min(value = 0L, message = "Amount must be positive.")
    private BigDecimal amount;
    private LocalDate recordDate;
}
