package com.example.controller;

import com.example.dto.AuthenticationResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.model.enums.Role;
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

    // todo: do i need object mapper?
    @PostMapping("/perform_login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) throws JsonProcessingException {
        AuthenticationResponse response = authService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.getToken())
                .body(objectMapper.writeValueAsString(response));
    }

    //todo: accountNumber taken: should handle exception but is 500
    @PostMapping("/perform_signup")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/account-verification")
    public ResponseEntity<Void> authenticateUser(@RequestParam String token) {
        jwtTokenService.enableUser(token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/role")
    public ResponseEntity<Boolean> getUsersRole() {
        return ResponseEntity.ok(
                this.authService.getCurrentUser().getRole() == Role.ADMIN);
    }
}