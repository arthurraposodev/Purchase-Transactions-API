package company.purchases.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Exchange rate entity object.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("exchange_rates")
public class ExchangeRate {

    @Id
    @NonNull
    private Long id;

    @Column("currency")
    @NonNull
    private String currency;

    @Column("exchange_rate")
    @JsonProperty("exchange_rate")
    @NonNull
    private BigDecimal rate;

    @Column("record_date")
    @JsonProperty("record_date")
    @NonNull
    private LocalDate recordDate;
}