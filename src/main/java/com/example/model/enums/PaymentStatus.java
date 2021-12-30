package com.example.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    PENDING("pending"), COMPLETED("completed"),
    CANCELED("canceled"), FAILED("failed");

    private final String value;
}