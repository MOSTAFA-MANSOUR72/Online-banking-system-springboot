package bank.management.system.mapper;

import bank.management.system.dto.AccountInfo;
import bank.management.system.entity.User;
import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
public class AccountInfoMapper implements Function<User, AccountInfo> {

    @Override
    public AccountInfo apply(User user) {
        return AccountInfo.builder()
                .accountName(user.getFirstName()+" "+user.getLastName())
                .accountBalance(user.getAccountBalance())
                .accountNumber(user.getAccountNumber())
                .status(user.getStatus())
                .build();
    }
}
