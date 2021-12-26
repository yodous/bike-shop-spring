package com.example.repository;

import com.example.model.Product;
import com.example.model.enums.ProductCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p where p.category=:category ")
    List<Product> findAllByCategoryWithPagination(@Param("category")ProductCategory category, Pageable pageable);
}
