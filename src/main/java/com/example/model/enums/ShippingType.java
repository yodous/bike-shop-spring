package com.example.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShippingType {
    SELF_PICKUP(0),
    PICKUP_AT_THE_POINT(10),
    DELIVERY_BY_COURIER(15);

	// TODO ML: final?
    private double price;
}
