package com.example.service;

import com.example.dto.RegisterRequest;
import com.example.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
}
