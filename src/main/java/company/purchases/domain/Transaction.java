package company.purchases.domain;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("transactions")
public class Transaction {
    @Id
    private Long id;

    @Column("description")
    private String description;

    @Setter
    @Column("amount")
    private BigDecimal amount;

    @Column("record_date")
    private LocalDate recordDate;
}
