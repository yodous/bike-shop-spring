package com.example.service;

import com.example.dto.ProductRequest;

public interface ProductMgmtService {

    int save(ProductRequest product);

    int update(int id, ProductRequest productRequest);

    void delete(int productId);

    int refreshDate(int id);
}
