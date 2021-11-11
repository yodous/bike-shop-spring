package com.example.service;

import com.example.dto.AuthenticationResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;

public interface AuthService {
    AuthenticationResponse login(LoginRequest loginRequest);

    void register(RegisterRequest registerRequest);

}
