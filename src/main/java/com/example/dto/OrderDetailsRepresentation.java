package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsRepresentation {
    private int paymentId;
    private List<OrderItemRepresentation> items;
    private LocalDate date;
    private double totalPrice;
    private String fullName;
    private String email;
    private String city;
    private String street;
    private String postalCode;
    private String paymentType;
    private String paymentStatus;
}
