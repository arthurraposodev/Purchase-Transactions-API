package company.purchases.domain.mapper;

import company.purchases.domain.Transaction;
import company.purchases.domain.dto.InputTransactionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransactionMapperTest {

    @Test
    @DisplayName("'ConvertToEntitySuccess' should be able to convert InputTransactionDTO to Transaction entity successfully")
    void convertToEntitySuccess() {
        // Arrange
        InputTransactionDTO inputTransactionDTO = new InputTransactionDTO();
        inputTransactionDTO.setDescription("Test Description");
        inputTransactionDTO.setAmount(BigDecimal.valueOf(100));
        inputTransactionDTO.setRecordDate(LocalDate.now());

        TransactionMapper transactionMapper = new TransactionMapper(new ModelMapper());

        // Act
        Transaction result = transactionMapper.convertToEntity(inputTransactionDTO);

        // Assert
        assertNotNull(result);
        assertEquals(inputTransactionDTO.getDescription(), result.getDescription());
        assertEquals(inputTransactionDTO.getAmount(), result.getAmount());
        assertEquals(inputTransactionDTO.getRecordDate(), result.getRecordDate());
    }

}