package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private char[] password;
    private char[] confirmPassword;
    private String firstName;
    private String lastName;
    private String email;
    private String accountNumber;
    private String city;
    private String street;
    private String postalCode;
    private String role;
}
