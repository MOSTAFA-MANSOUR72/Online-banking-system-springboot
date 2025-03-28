package bank.management.system.service.impl;

import bank.management.system.dto.*;
import bank.management.system.entity.Transaction;
import bank.management.system.entity.User;
import bank.management.system.mapper.AccountInfoMapper;
import bank.management.system.mapper.TransactionMapper;
import bank.management.system.repository.TransactionRepository;
import bank.management.system.repository.UserRepository;
import bank.management.system.service.TransactionService;
import bank.management.system.utils.AccountUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final AccountInfoMapper accountInfoMapper;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    @Override
    public BankResponse depositCreditAccount(CreditRequest request, String username) {



        User userCredit =  userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("not found user exception"));

        negativeAmountChecker(request.getAmount());// check for value validation

        userCredit.setAccountBalance(request
                .getAmount()
                .add(userCredit.getAccountBalance())
        );
        userRepository.save(userCredit);
        saveTransaction(Transaction.builder()
                .accountNumber(userCredit.getAccountNumber())
                .type("deposit")
                .amount(request.getAmount())
                .build()
        );
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DEPOSIT_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_DEPOSIT_SUCCESS_MESSAGE)
                .accountInfo(accountInfoMapper.apply(userCredit))
                .build();
    }

    @Override
    public BankResponse withdrawCreditAccount(CreditRequest request, String username) {


        negativeAmountChecker(request.getAmount());// check for value validation

        User userCredit = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("not found user exception"));

        withdrawChecker(request.getAmount(), userCredit.getAccountBalance());// check for withdraw value validation

        userCredit.setAccountBalance(userCredit.getAccountBalance().subtract(request.getAmount()));
        userRepository.save(userCredit);
        saveTransaction(Transaction.builder()
                .accountNumber(userCredit.getAccountNumber())
                .type("withdraw")
                .amount(request.getAmount())
                .build()
        );
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_WITHDRAW_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_WITHDRAW_SUCCESS_MESSAGE)
                .accountInfo(accountInfoMapper.apply(userCredit))
                .build();

    }

    @Override
    public BankResponse transfer(TransferRequest request) {
        // checking account existence:
       accountExistChecker(request.getSourceAccountNumber());
       accountExistChecker(request.getDestinationAccountNumber());

        // check for value validation
        negativeAmountChecker(request.getAmount());

        User sourceUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
        User destinationUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());


        withdrawChecker(request.getAmount(),sourceUser.getAccountBalance());// check for value amount

        sourceUser.setAccountBalance(sourceUser.getAccountBalance().subtract(request.getAmount()));
        destinationUser.setAccountBalance(destinationUser.getAccountBalance().add(request.getAmount()));
        userRepository.save(sourceUser);
        userRepository.save(destinationUser);
        saveTransaction(Transaction.builder()
                .accountNumber(sourceUser.getAccountNumber())
                .type("transfer")
                .amount(request.getAmount())
                .build()
        );
        saveTransaction(Transaction.builder()
                .accountNumber(destinationUser.getAccountNumber())
                .type("received")
                .amount(request.getAmount())
                .build()
        );
        return BankResponse.builder()
                .responseMessage(AccountUtils.ACCOUNT_WITHDRAW_SUCCESS_MESSAGE)
                .responseCode(AccountUtils.ACCOUNT_WITHDRAW_SUCCESS_CODE)
                .accountInfo(accountInfoMapper.apply(sourceUser))
                .build();
    }

    @Override
    public List<TransactionDto> getAllTransactions(EnquiryRequest enquiryRequest) {
        accountExistChecker(enquiryRequest.getAccountNumber());

        return transactionRepository
                .findAllByAccountNumber(enquiryRequest.getAccountNumber())
                .stream().map(transactionMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getTransactionsWithInRangeDate(String accountNumber, LocalDate startingDate, LocalDate endingDate) {
        return transactionRepository.findAllByAccountNumberAndCreatedAtIsBetween(accountNumber,startingDate, endingDate)
                .stream().map(transactionMapper)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto findById(long id) {
        Transaction transaction =transactionRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Transaction not found"));
        return transactionMapper.apply(transaction);
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    private void accountExistChecker( String accountNumber){
        // checking account existence:
        boolean isAccountExist = userRepository
                .existsByAccountNumber(accountNumber);
        if(!isAccountExist) {
            throw new RuntimeException("NOt found exception");
        }
    }

    private void negativeAmountChecker(BigDecimal amount){
        if(amount.longValue()<=0){
            throw new RuntimeException("Invalid deposit value exception");
        }
    }
    private void withdrawChecker(BigDecimal amount, BigDecimal balance){
        if(amount.longValue()>balance.longValue()){
            throw new RuntimeException("Invalid Withdraw value exception");
        }
    }
}
