package com.example.controller;

import com.example.dto.AuthenticationResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.security.JwtTokenService;
import com.example.service.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class AuthControllerTest {
    @Mock
    private AuthService authService;
    @Mock
    private JwtTokenService jwtTokenService;
    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidRequestOnLoginMethod_ShouldSucceed() {
        given(authService.login(any())).willReturn(new AuthenticationResponse());
        ResponseEntity<String> response = authController.login(new LoginRequest());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().get(HttpHeaders.AUTHORIZATION)).isNotEmpty();
    }

    @Test
    void givenRegisterRequest_withValidCredentials_thenStatus200() {
        ResponseEntity<String> response = authController.register(new RegisterRequest());

        Assertions.assertDoesNotThrow(() -> authService.register(any()));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("email").contains("sent");
    }

    @Test
    void givenAuthenticateUserRequest_thenOk() {
        given(jwtTokenService.generateAccessToken(any())).willReturn("test_token");
        ResponseEntity<String> response = authController.authenticateUser("test_token");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("account").contains("activated");
    }
}
