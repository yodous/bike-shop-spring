package com.example.service;

import com.example.dto.ProductCreateRequest;
import com.example.dto.ProductDTO;
import com.example.dto.ProductUpdate;

import java.util.List;

public interface ProductService {
    ProductDTO get(int productId);

    List<ProductDTO> getAll();

    List<ProductDTO> getAllByUsername(String username);

    List<ProductDTO> getByCategory(String categoryName);

    List<ProductDTO> getByString(String str);

    int create(ProductCreateRequest product);

    int update(int productId, ProductUpdate product);

    int updatePrice(int productId, double newPrice);

    void delete(int productId);

}
