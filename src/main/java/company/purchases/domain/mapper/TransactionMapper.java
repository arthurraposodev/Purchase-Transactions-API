package company.purchases.domain.mapper;

import company.purchases.domain.Transaction;
import company.purchases.domain.dto.ConvertedOutputTransactionDTO;
import company.purchases.domain.dto.OutputTransactionDTO;
import company.purchases.domain.dto.InputTransactionDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Transaction mapper for mapping to and from the Transaction Entity and its DTOs.
 */
@RequiredArgsConstructor
@Component
public class TransactionMapper {
    private final ModelMapper modelMapper;

    /**
     * Convert to entity transaction.
     *
     * @param inputTransactionDTO the input transaction dto
     * @return the transaction
     */
    public Transaction convertToEntity(InputTransactionDTO inputTransactionDTO) {
        return modelMapper.map(inputTransactionDTO, Transaction.class);
    }

    /**
     * Convert to output transaction dto output transaction dto.
     *
     * @param transaction the transaction
     * @return the output transaction dto
     */
    public OutputTransactionDTO convertToOutputTransactionDTO(Transaction transaction) {
        return modelMapper.map(transaction, OutputTransactionDTO.class);
    }

    /**
     * Convert to converted output transaction dto converted output transaction dto.
     *
     * @param transaction     the transaction
     * @param exchangeRate    the exchange rate
     * @param convertedAmount the converted amount
     * @return the converted output transaction dto
     */
    public ConvertedOutputTransactionDTO convertToConvertedOutputTransactionDTO(Transaction transaction, BigDecimal exchangeRate, BigDecimal convertedAmount) {
        ConvertedOutputTransactionDTO convertedOutputTransactionDTO = modelMapper.map(transaction, ConvertedOutputTransactionDTO.class);
        convertedOutputTransactionDTO.setExchangeRate(exchangeRate);
        convertedOutputTransactionDTO.setConvertedAmount(convertedAmount);
        return convertedOutputTransactionDTO;
    }
}
