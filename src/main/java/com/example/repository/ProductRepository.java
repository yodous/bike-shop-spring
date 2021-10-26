package com.example.repository;

import com.example.model.Product;
import com.example.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllBySellerUsernameIgnoreCase(String username);

    List<Product> findAllByNameContaining(String name);

    List<Product> findAllByCategory(ProductCategory category);
}
