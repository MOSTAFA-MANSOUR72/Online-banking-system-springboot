package bank.management.system.controller;

import bank.management.system.dto.*;
import bank.management.system.entity.User;
import bank.management.system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(
                userService.findAll()
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<BankResponse> profileEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return ResponseEntity.ok(
                userService.balanceEnquiry(enquiryRequest)
        );
    }



}
