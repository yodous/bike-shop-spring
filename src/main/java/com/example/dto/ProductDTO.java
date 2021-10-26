package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private String category;
    private double price;
    private Instant lastModified;
}
