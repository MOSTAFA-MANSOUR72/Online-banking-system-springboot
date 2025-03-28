package bank.management.system.controller;

import bank.management.system.dto.*;
import bank.management.system.entity.SystemRoles;
import bank.management.system.entity.User;
import bank.management.system.service.UserService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Var;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(
                userService.findAll()
        );
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<BankResponse> profileEnquiry(Authentication p ){

        return ResponseEntity.ok(
                userService.balanceEnquiry(p.getName())
        );
    }

    @DeleteMapping
    @PreAuthorize("hasRole('admin')")
    public void deleteAll(){
        userService.deleteAll();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public void deleteById(@PathVariable long id){
        userService.deleteUserById(id);
    }

    @DeleteMapping("/delete-my-account")
    @PreAuthorize("hasRole('user')")
    public void deleteMyAccount(Authentication p){
        String username = p.getName();
        userService.deleteByUserName(username);
    }

}
