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
    private String sellerName;
    private double totalPrice;
    private List<OrderItemRepresentation> items;
    private String paymentType;
    private String paymentStatus;
    private LocalDate date;
}
