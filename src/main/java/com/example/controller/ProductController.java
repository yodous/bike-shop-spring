package com.example.controller;

import com.example.dto.ProductView;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    //todo: try to refactor products component to not need this method
    @GetMapping("/count")
    public int countProducts() {
        return productService.count();
    }

    //todo: return basic product view
    @GetMapping
    public ResponseEntity<List<ProductView>> getAll(@RequestParam int page,
                                                    @RequestParam int size) {
        List<ProductView> products = productService.getAll(page, size);
        log.info("size=" + products.size() + "page=" + page + ", size=" + size);
        return ResponseEntity.ok(products);
    }

    //todo: return detailed product view
    @GetMapping("/by-name")
    public ResponseEntity<List<ProductView>> getByName(@RequestParam String name,
                                                       @RequestParam int page,
                                                       @RequestParam int size) {
        return ResponseEntity.ok(productService.getByNamePaginated(name, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductView> getById(@PathVariable int id) {
        ProductView product = productService.get(id);
        log.info("product: " + product);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<List<ProductView>> getByCategory(@PathVariable String category,
                                                           @RequestParam int page,
                                                           @RequestParam int size) {
        return ResponseEntity.ok(productService.getByCategoryPaginated(category, page, size));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<List<ProductView>> getByUsername(@PathVariable String username,
                                                           @RequestParam int page,
                                                           @RequestParam int size) {
        return ResponseEntity.ok(productService.getAllByUsernamePaginated(username, page, size));
    }
}