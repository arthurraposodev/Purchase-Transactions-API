package company.purchases.domain.mapper;

import company.purchases.domain.Transaction;
import company.purchases.domain.dto.OutputTransactionDTO;
import company.purchases.domain.dto.InputTransactionDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TransactionMapper {
    private final ModelMapper modelMapper;

    public Transaction convertToEntity(InputTransactionDTO inputTransactionDTO) {
        return modelMapper.map(inputTransactionDTO, Transaction.class);
    }

    public OutputTransactionDTO convertToOutputTransactionDTO(Transaction transaction) {
        return modelMapper.map(transaction, OutputTransactionDTO.class);
    }
}
