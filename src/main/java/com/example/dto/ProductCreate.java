package com.example.dto;

import com.example.model.Product;
import lombok.Getter;

@Getter
public class ProductCreate {
    private String name;
    private String description;
    private Product.Category category;
    private double price;
}
