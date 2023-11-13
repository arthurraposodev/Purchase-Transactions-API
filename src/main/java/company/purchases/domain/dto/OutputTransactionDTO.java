package company.purchases.domain.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OutputTransactionDTO {
    private Long id;
    @Size(max = 50)
    private String description;
    private BigDecimal amount;
    private LocalDate recordDate;
}
