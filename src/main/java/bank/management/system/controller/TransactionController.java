package bank.management.system.controller;

import bank.management.system.dto.*;
import bank.management.system.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("deposit")
    public BankResponse DepositRequest(@RequestBody CreditRequest request, Authentication p){
        return transactionService.depositCreditAccount(request, p.getName());
    }

    @PostMapping("withdraw")
    public BankResponse WithdrawRequest(@RequestBody CreditRequest request, Authentication p){
        return transactionService.withdrawCreditAccount(request, p.getName());
    }

    @PostMapping("transfer")
    public BankResponse TransferRequest(@RequestBody TransferRequest request){
        return transactionService.transfer(request);
    }

    @GetMapping("transactions")
    public List<TransactionDto> getAllTransactions(@RequestBody EnquiryRequest enquiryRequest){
        return transactionService.getAllTransactions(enquiryRequest);
    }

    @GetMapping("transactions/filter/date")
    public List<TransactionDto> getAllTransactionsWithInRangeDate(@RequestBody TransactionRequest request){
        return transactionService.getTransactionsWithInRangeDate(request.getAccountNumber(), request.getStartingDate(), request.getEndingDate());
    }

    @GetMapping("transactions/{id}")
    public TransactionDto getTransactionById(@PathVariable("id") long id){
        return transactionService.findById(id);
    }
}
