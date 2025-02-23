package bank.management.system.service.impl;

import bank.management.system.dto.*;
import bank.management.system.dto.authentication.LoginRequest;
import bank.management.system.dto.authentication.AuthenticationResponse;
import bank.management.system.dto.authentication.RegisterRequest;
import bank.management.system.entity.model.userDetails;
import bank.management.system.entity.Roles;
import bank.management.system.entity.User;
import bank.management.system.mapper.AuthenticationMapper;
import bank.management.system.repository.UserRepository;
import bank.management.system.security.jwt.JwtService;
import bank.management.system.service.EmailService;
import bank.management.system.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationMapper authenticationMapper;

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest userRequest) {
        gmailExistChecker(userRequest.getEmail());

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName((userRequest.getLastName()))
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .phoneNumber(userRequest.getPhoneNumber())
                .email(userRequest.getEmail())
                .status("ACTIVE")
                .roles(Roles.user)
                .build();

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(new userDetails(user));

        sendGmailMessage(savedUser);

        AuthenticationResponse response =authenticationMapper.apply(savedUser);
        response.setToken(token);
        return response;
    }

    public AuthenticationResponse authenticate(LoginRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var savedUser = userRepository.findByUsername(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));

        String token = jwtService.generateToken(new userDetails(savedUser));

        AuthenticationResponse response = authenticationMapper.apply(savedUser);
        response.setToken(token);

        return response;
    }

    private void sendGmailMessage(User savedUser) {
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("Account Creation.")
                .messageBody("Congratulation!... Account Created..Successfully.\n Account Details:\n" +
                        "Account Name: "+savedUser.getFirstName()+" "+savedUser.getLastName()+
                        "\nAccount Number: "+savedUser.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDetails);
    }
    private void gmailExistChecker(String gmail){
        if(existByEmail(gmail)){
            throw new RuntimeException("gmail already exist exception");
        }
    }
    private Boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
