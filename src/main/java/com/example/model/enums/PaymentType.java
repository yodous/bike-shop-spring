package com.example.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentType {
    CARD("card"),
    BLIK("blik"),
    TRANSFER("transfer");

    private final String value;
}