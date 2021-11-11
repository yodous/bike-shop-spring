package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ProductView {
    private String name;
    private String category;
    private double price;
    private Instant lastModified;
}
