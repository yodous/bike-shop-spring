package com.example.controller;

import com.example.dto.AuthenticationResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.security.JwtTokenService;
import com.example.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        AuthenticationResponse response = authService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.getToken())
                .body("You have been logged in");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.ok("Activation email has been sent");
    }

    @PostMapping("/account-verification")
    public ResponseEntity<String> authenticateUser(@RequestParam String token) {
        jwtTokenService.enableUser(token);
        return ResponseEntity.ok("Your account has been activated");
    }

}