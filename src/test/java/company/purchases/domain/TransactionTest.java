package company.purchases.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    @DisplayName("Transaction object can be created with valid input values")
    void createTransactionWithValidInputValuesTest() {
        // Arrange
        Long id = 1L;
        String description = "Test Description";
        BigDecimal amount = BigDecimal.valueOf(100);
        LocalDate recordDate = LocalDate.now();

        // Act
        Transaction transaction = new Transaction(id,description,amount,recordDate);

        // Assert
        assertNotNull(transaction);
        assertEquals(id, transaction.getId());
        assertEquals(description, transaction.getDescription());
        assertEquals(amount, transaction.getAmount());
        assertEquals(recordDate, transaction.getRecordDate());
    }

    @Test
    @DisplayName("Transaction object can be updated with valid input values")
    void updateTransactionWithValidInputValuesTest() {
        // Arrange
        Long id = 1L;
        String description = "Test Description";
        BigDecimal amount = BigDecimal.valueOf(100);
        LocalDate recordDate = LocalDate.now();

        // Act
        Transaction transaction = new Transaction(id,description,amount,recordDate);

        // Assert
        assertEquals(id, transaction.getId());
        assertEquals(description, transaction.getDescription());
        assertEquals(amount, transaction.getAmount());
        assertEquals(recordDate, transaction.getRecordDate());
    }

    @Test
    @DisplayName("Transaction object can be compared with another Transaction object with same values")
    void compareTransactionWithSameValuesTest() {
        // Arrange
        Transaction transaction1 = new Transaction(1L,"Test Description",BigDecimal.valueOf(100),LocalDate.of(2023,12,1));
        Transaction transaction2 = new Transaction(1L,"Test Description",BigDecimal.valueOf(100),LocalDate.of(2023,12,1));

        // Assert
        assertEquals(transaction1.getId(), transaction2.getId());
        assertEquals(transaction1.getDescription(), transaction2.getDescription());
        assertEquals(transaction1.getRecordDate(), transaction2.getRecordDate());
        assertEquals(transaction1.getAmount(), transaction2.getAmount());
    }

    @Test
    @DisplayName("Transaction object cannot be compared with another Transaction object with different values")
    void compareTransactionWithDifferentValuesTest() {
        // Arrange
        Transaction transaction1 = new Transaction(1L,"Test Description",BigDecimal.valueOf(100),LocalDate.now());
        Transaction transaction2 = new Transaction(1L,"Test Description",BigDecimal.valueOf(200),LocalDate.now());

        // Assert
        assertNotEquals(transaction1, transaction2);
    }

}