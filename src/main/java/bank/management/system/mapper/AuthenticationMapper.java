package bank.management.system.mapper;

import bank.management.system.dto.AccountInfo;
import bank.management.system.dto.authentication.AuthenticationResponse;
import bank.management.system.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AuthenticationMapper implements Function<User, AuthenticationResponse> {

    @Override
    public AuthenticationResponse apply(User savedUser) {
        return AuthenticationResponse.builder()
                .accountInfo(
                        AccountInfo.builder()
                                .accountNumber(savedUser.getAccountNumber())
                                .status(savedUser.getStatus())
                                .accountName(savedUser.getFirstName()+" "+savedUser.getLastName())
                                .accountBalance(savedUser.getAccountBalance())
                                .build()
                )
                .build();
    }
}
