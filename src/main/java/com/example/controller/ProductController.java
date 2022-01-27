package com.example.controller;

import com.example.dto.ProductRepresentation;
import com.example.dto.ProductsResponse;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ProductsResponse> getAll(@RequestParam int page,
                                                   @RequestParam int size) {
        return ResponseEntity.ok(productService.getAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRepresentation> getById(@PathVariable int id) {
        return ResponseEntity.ok(productService.get(id));
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<ProductsResponse> getByCategory(@PathVariable String category,
                                                                     @RequestParam int page,
                                                                     @RequestParam int size) {
        return ResponseEntity.ok(productService.getByCategoryPaginated(category, page, size));
    }

}