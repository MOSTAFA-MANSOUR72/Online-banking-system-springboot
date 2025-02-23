package bank.management.system.service;

import bank.management.system.dto.*;
import bank.management.system.entity.User;

import java.util.List;

public interface UserService {

    User findById(long id);
    List<User> findAll();
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
}
