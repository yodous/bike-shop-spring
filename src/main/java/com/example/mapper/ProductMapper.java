package com.example.mapper;

import com.example.dto.ProductDTO;
import com.example.model.Product;

import java.time.LocalDate;
import java.time.ZoneId;

public class ProductMapper {
    public static ProductDTO mapToDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getModifiedAt());
    }
}
