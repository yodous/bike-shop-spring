package com.example.service;

import com.example.dto.ProductRepresentation;

import java.util.List;

public interface ProductService {
    ProductRepresentation get(int productId);

    List<ProductRepresentation> getAll(int page, int size);

    List<ProductRepresentation> getByCategoryPaginated(String categoryName, int page, int size);

    List<ProductRepresentation> getByNamePaginated(String productName, int page, int size);


    int countCategoryProducts(String category);

    long count();
}
