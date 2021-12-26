package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderForm {
    private String fullName;
    private String email;
    private String accountNumber;
    private String city;
    private String street;
    private String postalCode;
}
