package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class PaymentRepresentation {
    private double totalPrice;
    private String paymentType;
    private String paymentStatus;
    private Instant paymentDate;
}
