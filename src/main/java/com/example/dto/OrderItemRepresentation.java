package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderItemRepresentation {
    private String sellerUsername;
    private String productName;
    private int quantity;
    private double productPrice;
    private LocalDateTime orderedAt;
    private String paymentStatus;
    private double totalPrice;
}
