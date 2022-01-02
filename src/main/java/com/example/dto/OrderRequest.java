package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private List<OrderItemRequest> items;
    private String fullName;
    private String email;
    private String city;
    private String street;
    private String postalCode;
    private String paymentType;
}
