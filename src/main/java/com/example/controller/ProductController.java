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
    public ResponseEntity<List<ProductView>> getByString(@RequestParam String name) {
        return ResponseEntity.ok(productService.getByString(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductView> getById(@PathVariable int id) {
        return ResponseEntity.ok(productService.get(id));
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<List<ProductView>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getByCategory(category));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<List<ProductView>> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(productService.getAllByUsername(username));
    }
}