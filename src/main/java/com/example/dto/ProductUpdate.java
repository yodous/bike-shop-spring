package com.example.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ProductUpdate {
    private String name;
    private String description;
    private ProductCategory category;
    private double price;
    private final Instant modifiedAt = Instant.now();

    public ProductUpdate(String name, String description,
                         ProductCategory category, double price) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }
}
