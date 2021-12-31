package com.example.service;

import com.example.dto.ProductRequest;
import com.example.dto.ProductView;

import java.util.List;

public interface ProductService {
    ProductView get(int productId);

    List<ProductView> getAll(int page, int size);

    List<ProductView> getByCategoryPaginated(String categoryName, int page, int size);

    List<ProductView> getByNamePaginated(String productName, int page, int size);

    int save(ProductRequest product);

    int update(int id, ProductRequest productRequest);

    void delete(int productId);

    int refreshDate(int id);

    int count();

}
