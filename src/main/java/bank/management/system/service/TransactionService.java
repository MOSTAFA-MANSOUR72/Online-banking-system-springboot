package bank.management.system.service;

import bank.management.system.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    BankResponse depositCreditAccount(CreditRequest creditRequest);
    BankResponse withdrawCreditAccount(CreditRequest creditRequest);
    BankResponse transfer(TransferRequest request);
    List<TransactionDto> getAllTransactions(EnquiryRequest enquiryRequest);
    List<TransactionDto> getTransactionsWithInRangeDate(String accountNumber, LocalDate startingDate, LocalDate EndingDate);
    TransactionDto findById(long id);
}
