package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String city;
    private String email;
    private String street;
    private String postalCode;
}
