package com.example.controller;

import com.example.dto.ProductCreate;
import com.example.dto.ProductDTO;
import com.example.model.ProductUpdate;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getOne(@PathVariable int id) {
        return new ResponseEntity<>(productService.getOne(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProductCreate productCreate) {
        productService.create(productCreate);
        return new ResponseEntity<>("Product has been saved", HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePrice(@PathVariable int id, @RequestBody double newPrice) {
        productService.updatePrice(id, newPrice);
        return new ResponseEntity<>("Product price has been updated", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody ProductUpdate product) {
        productService.update(id, product);

        return new ResponseEntity<>("Product has been updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        productService.delete(id);

        return new ResponseEntity<>("Product has been deleted", HttpStatus.OK);
//        return new ResponseEntity<>("Product does not exist", HttpStatus.NOT_FOUND);
    }

}
