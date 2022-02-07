package com.example.controller;

import com.example.dto.AuthenticationResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.security.JwtTokenService;
import com.example.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

	// TODO ML: declared checked Exception is never thrown;
    @PostMapping(value = "/perform_login")
	// TODO ML: why marked as @Valid? I do not see anything, which could utilize it
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request) throws JsonProcessingException {
        AuthenticationResponse response = authService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.getToken())
                .body(response);
    }

    @PostMapping("/perform_signup")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
		// TODO ML: personally I prefer `ResponseEntity.status(HttpStatus.CREATED).build()` but it doesn't matter
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/account-verification")
    public ResponseEntity<Void> authenticateUser(@RequestParam String token) {
        jwtTokenService.enableUser(token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
