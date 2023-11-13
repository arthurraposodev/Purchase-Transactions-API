package company.purchases.domain.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InputTransactionDTOTest {


    @Test
    @DisplayName("Can create an instance of InputTransactionDTO with valid values for all fields")
    void createInstanceWithValidValuesTest() {
        InputTransactionDTO inputTransactionDTO = new InputTransactionDTO();
        inputTransactionDTO.setDescription("Test Description");
        inputTransactionDTO.setAmount(BigDecimal.valueOf(100));
        inputTransactionDTO.setRecordDate(LocalDate.now());

        assertNotNull(inputTransactionDTO.getDescription());
        assertNotNull(inputTransactionDTO.getAmount());
        assertNotNull(inputTransactionDTO.getRecordDate());
    }

    @Test
    @DisplayName("The 'description' field can be set to a string with a length of 50 characters or less")
    void setDescriptionWithLength50OrLessTest() {
        InputTransactionDTO inputTransactionDTO = new InputTransactionDTO();
        String description = "Test Description";
        inputTransactionDTO.setDescription(description);

        assertEquals(description, inputTransactionDTO.getDescription());
    }

    @Test
    @DisplayName("The 'amount' field can be set to a positive BigDecimal value")
    void setPositiveAmountTest() {
        InputTransactionDTO inputTransactionDTO = new InputTransactionDTO();
        BigDecimal amount = BigDecimal.valueOf(100);
        inputTransactionDTO.setAmount(amount);

        assertEquals(amount, inputTransactionDTO.getAmount());
    }

    @Test
    @DisplayName("The 'description' field can be set to an empty string")
    void setEmptyDescriptionTest() {
        InputTransactionDTO inputTransactionDTO = new InputTransactionDTO();
        String description = "";
        inputTransactionDTO.setDescription(description);

        assertEquals(description, inputTransactionDTO.getDescription());
    }

    @Test
    @DisplayName("The 'description' field can be set to a string with a length greater than 50 characters")
    void setDescriptionWithLengthGreaterThan50Test() {
        InputTransactionDTO inputTransactionDTO = new InputTransactionDTO();
        String description = "This is a description with more than 50 characters. This is a description with more than 50 characters.";
        inputTransactionDTO.setDescription(description);

        assertEquals(description, inputTransactionDTO.getDescription());
    }

    @Test
    @DisplayName("The 'amount' field can be set to a BigDecimal value of zero")
    void setZeroAmountTest() {
        InputTransactionDTO inputTransactionDTO = new InputTransactionDTO();
        BigDecimal amount = BigDecimal.ZERO;
        inputTransactionDTO.setAmount(amount);

        assertEquals(amount, inputTransactionDTO.getAmount());
    }

}