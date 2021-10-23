package com.example.dto;

import com.example.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private Product.Category category;
    private double price;
    private LocalDateTime lastModified;
}
