package com.example.controller;

import com.example.dto.ProductView;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductView>> getAll(@RequestParam int page,
                                                    @RequestParam int size) {
        return ResponseEntity.ok(productService.getAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductView> getById(@PathVariable int id) {
        return ResponseEntity.ok(productService.get(id));
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<List<ProductView>> getByCategory(@PathVariable String category,
                                                           @RequestParam int page,
                                                           @RequestParam int size) {
        return ResponseEntity.ok(productService.getByCategoryPaginated(category, page, size));
    }

    @GetMapping("/count/{categoryName}")
    public int countProducts(@PathVariable String categoryName) {
        return productService.countCategoryProducts(categoryName);
    }
}