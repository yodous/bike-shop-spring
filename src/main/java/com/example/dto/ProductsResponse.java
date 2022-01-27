package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class ProductsResponse {
    private List<ProductRepresentation> products;
    private int size;

    public List<ProductRepresentation> getProducts() {
        return products;
    }

    public int getSize() {
        return products.size();
    }
}
