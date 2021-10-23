package com.example.service;

import com.example.dto.ProductCreate;
import com.example.dto.ProductDTO;
import com.example.dto.ProductPriceUpdate;
import com.example.model.Product;
import com.example.model.ProductUpdate;

import java.util.List;

public interface ProductService {
    void create(ProductCreate product);
    List<ProductDTO> getAll();
    ProductDTO getOne(int productId);
    void delete(int productId);
    void updatePrice(int productId, double newPrice);
    void update( int productId, ProductUpdate product);
}
