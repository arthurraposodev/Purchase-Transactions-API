package company.purchases.domain.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OutputTransactionDTOTest {

    @Test
    @DisplayName("OutputTransactionDTO object can be instantiated with valid input values")
    void instantiationWithValidInputValuesTest() {
        OutputTransactionDTO outputTransactionDTO = new OutputTransactionDTO();
        assertNotNull(outputTransactionDTO);
    }

    @Test
    @DisplayName("Getter methods return the correct values for all attributes")
    void getterMethodsReturnCorrectValuesTest() {
        OutputTransactionDTO outputTransactionDTO = new OutputTransactionDTO();
        outputTransactionDTO.setId(1L);
        outputTransactionDTO.setDescription("Test Description");
        outputTransactionDTO.setAmount(BigDecimal.valueOf(100));
        outputTransactionDTO.setRecordDate(LocalDate.now());

        assertEquals(1L, outputTransactionDTO.getId());
        assertEquals("Test Description", outputTransactionDTO.getDescription());
        assertEquals(BigDecimal.valueOf(100), outputTransactionDTO.getAmount());
        assertEquals(LocalDate.now(), outputTransactionDTO.getRecordDate());
    }

    @Test
    @DisplayName("Setter methods correctly update the values of all attributes")
    void setterMethodsCorrectlyUpdateValuesTest() {
        OutputTransactionDTO outputTransactionDTO = new OutputTransactionDTO();
        outputTransactionDTO.setId(1L);
        outputTransactionDTO.setDescription("Test Description");
        outputTransactionDTO.setAmount(BigDecimal.valueOf(100));
        outputTransactionDTO.setRecordDate(LocalDate.now());

        outputTransactionDTO.setId(2L);
        outputTransactionDTO.setDescription("Updated Description");
        outputTransactionDTO.setAmount(BigDecimal.valueOf(200));
        outputTransactionDTO.setRecordDate(LocalDate.now().plusDays(1));

        assertEquals(2L, outputTransactionDTO.getId());
        assertEquals("Updated Description", outputTransactionDTO.getDescription());
        assertEquals(BigDecimal.valueOf(200), outputTransactionDTO.getAmount());
        assertEquals(LocalDate.now().plusDays(1), outputTransactionDTO.getRecordDate());
    }

    @Test
    @DisplayName("Description attribute can be set to an empty string")
    void descriptionAttributeCanBeSetToEmptyStringTest() {
        OutputTransactionDTO outputTransactionDTO = new OutputTransactionDTO();
        outputTransactionDTO.setDescription("");

        assertEquals("", outputTransactionDTO.getDescription());
    }

    @Test
    @DisplayName("Description attribute can be set to a string with 50 characters")
    void descriptionAttributeCanBeSetToStringWith50CharactersTest() {
        OutputTransactionDTO outputTransactionDTO = new OutputTransactionDTO();
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        outputTransactionDTO.setDescription(description);

        assertEquals(description, outputTransactionDTO.getDescription());
    }

    @Test
    @DisplayName("Amount attribute can be set to a value of 0")
    void amountAttributeCanBeSetToZeroTest() {
        OutputTransactionDTO outputTransactionDTO = new OutputTransactionDTO();
        outputTransactionDTO.setAmount(BigDecimal.ZERO);

        assertEquals(BigDecimal.ZERO, outputTransactionDTO.getAmount());
    }

}