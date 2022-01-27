package com.example.service;

import com.example.dto.ProductRepresentation;
import com.example.dto.ProductsResponse;

public interface ProductService {
    ProductRepresentation get(int productId);

    ProductsResponse getAll(int page, int size);

    ProductsResponse getByCategoryPaginated(String categoryName, int page, int size);
}
