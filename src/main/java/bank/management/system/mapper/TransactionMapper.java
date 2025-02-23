package bank.management.system.mapper;

import bank.management.system.dto.TransactionDto;
import bank.management.system.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TransactionMapper implements Function<Transaction, TransactionDto> {
    @Override
    public TransactionDto apply(Transaction transaction) {
        return TransactionDto.builder()
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .accountNumber(transaction.getAccountNumber())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
