package com.example.service;

import com.example.dto.ProductRequest;
import com.example.dto.ProductView;

import java.util.List;

public interface ProductService {
    ProductView get(int productId);

    List<ProductView> getAll();

    List<ProductView> getAllByUsername(String username);

    List<ProductView> getByCategory(String categoryName);

    List<ProductView> getByString(String str);

    int save(ProductRequest product);

    int update(int id, ProductRequest productRequest);

    int updatePrice(int productId, double newPrice);

    void delete(int productId);

}
