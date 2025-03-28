package bank.management.system.controller;

import bank.management.system.dto.authentication.LoginRequest;
import bank.management.system.dto.authentication.AuthenticationResponse;
import bank.management.system.dto.authentication.RegisterRequest;
import bank.management.system.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/user/register")
    public ResponseEntity<AuthenticationResponse> userRegister(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService
                .userRegister(request)
        );
    }

    @PostMapping("/admin/register")
    public ResponseEntity<AuthenticationResponse> adminRegister(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(
                authenticationService.adminRegister(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService
                .authenticate(request)
        );
    }
}
