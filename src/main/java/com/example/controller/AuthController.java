package com.example.controller;

import com.example.dto.AuthenticationResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.security.JwtTokenService;
import com.example.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtTokenService jwtTokenService;
    private final ObjectMapper objectMapper;

    @PostMapping("/perform_login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) throws JsonProcessingException {
        AuthenticationResponse response = authService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.getToken())
                .body(objectMapper.writeValueAsString(response));
    }

    @PostMapping("/perform_signup")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return new ResponseEntity<>("Activation email has been sent", HttpStatus.CREATED);
    }

    @PostMapping("/account-verification")
    public ResponseEntity<String> authenticateUser(@RequestParam String token) {
        jwtTokenService.enableUser(token);
        return new ResponseEntity<>("Your account has been activated", HttpStatus.CREATED);
    }

}