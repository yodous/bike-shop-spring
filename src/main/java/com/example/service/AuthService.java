package com.example.service;

import com.example.dto.AuthenticationResponse;
import com.example.dto.LoginRequest;
import com.example.dto.SignupRequest;

public interface AuthService {
    AuthenticationResponse login(LoginRequest loginRequest);

    void signup(SignupRequest signupRequest);

    void enableUser(String token);
}
