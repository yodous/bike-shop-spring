package com.example.dto;

import com.example.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;

@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private User user;
}
