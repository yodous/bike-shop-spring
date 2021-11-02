package com.example.service;

import com.example.dto.RegisterRequest;
import com.example.dto.RegisterResponse;

public interface UserService {
    RegisterResponse register(RegisterRequest registerRequest);
}
