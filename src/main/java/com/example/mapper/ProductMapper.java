package com.example.mapper;

import com.example.dto.ProductDTO;
import com.example.model.Product;

public class ProductMapper {
    public static ProductDTO mapToDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getCategory().getName(),
                product.getPrice(),
                product.getModifiedAt());
    }
}
