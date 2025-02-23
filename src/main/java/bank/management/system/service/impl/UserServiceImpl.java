package bank.management.system.service.impl;

import bank.management.system.dto.*;
import bank.management.system.entity.User;
import bank.management.system.repository.UserRepository;
import bank.management.system.service.EmailService;
import bank.management.system.service.UserService;
import bank.management.system.utils.AccountUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        // checking account existence:
        userExistChecker(enquiryRequest.getAccountNumber());

        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountNumber(foundUser.getAccountNumber())
                        .status(foundUser.getStatus())
                        .accountName(foundUser.getFirstName()+" "+foundUser.getLastName())
                        .accountBalance(foundUser.getAccountBalance())
                        .build())
                .build();
    }

    private void userExistChecker(String accountNumber){
        boolean isAccountExist = userRepository
                .existsByAccountNumber(accountNumber);
        if(!isAccountExist) {
            throw new RuntimeException("not found user exception");
        }
    }
}
