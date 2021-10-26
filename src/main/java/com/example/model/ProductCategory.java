package com.example.model;

import lombok.Getter;

@Getter
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

    private String name;

    ProductCategory(String name) {
        this.name = name;
    }
}