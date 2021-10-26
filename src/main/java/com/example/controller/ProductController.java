package com.example.controller;

import com.example.dto.ProductDTO;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/listing")
    public ResponseEntity<List<ProductDTO>> getByString(@RequestParam String string) {
        return ResponseEntity.ok(productService.getByString(string));
    }

    @GetMapping("/sale/{id}")
    public ResponseEntity<ProductDTO> getOne(@PathVariable int id) {
        return ResponseEntity.ok(productService.get(id));
    }

    @GetMapping("/sale/{username}")
    public ResponseEntity<List<ProductDTO>> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(productService.getAllByUsername(username));
    }

}
