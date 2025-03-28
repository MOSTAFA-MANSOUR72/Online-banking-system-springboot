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
    public BankResponse balanceEnquiry(String username) {

        // checking account existence:
        User foundUser = userExistChecker(username);

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

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteByUserName(String name) {
        System.out.println("name : "+name);
        User user = userRepository.findByUsername(name)
                .orElseThrow(()-> new RuntimeException("not found user exception"));
        userRepository.deleteById(user.getId());


    }

    private User userExistChecker(String accountNumber){
        return userRepository
                .findByUsername(accountNumber)
                .orElseThrow(()->new RuntimeException("not found user exception"));
    }
}
