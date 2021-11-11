package com.example.service;

import com.example.dto.ProductCreateRequest;
import com.example.dto.ProductView;
import com.example.dto.ProductUpdate;

import java.util.List;

public interface ProductService {
    ProductView get(int productId);

    List<ProductView> getAll();

    List<ProductView> getAllByUsername(String username);

    List<ProductView> getByCategory(String categoryName);

    List<ProductView> getByString(String str);

    int create(ProductCreateRequest product);

    int update(int productId, ProductUpdate product);

    int updatePrice(int productId, double newPrice);

    void delete(int productId);

}
