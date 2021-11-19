package com.example.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategory {
    BEAUTY("beauty"),
    ENTERTAINMENT("entertainment"),
    ELECTRONICS("electronics"),
    FASHION("fashion"),
    HEALTH("health"),
    HOUSE("house"),
    MOTORIZATION("motorization"),
    PROPERTY("property"),
    SPORT("sport");

    private final String value;
}