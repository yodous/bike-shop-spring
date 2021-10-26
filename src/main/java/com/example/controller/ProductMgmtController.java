package com.example.controller;

import com.example.dto.ProductCreateRequest;
import com.example.dto.ProductUpdate;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class ProductMgmtController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProductCreateRequest productCreateRequest) {
        productService.create(productCreateRequest);
        return new ResponseEntity<>("Product has been saved", HttpStatus.CREATED);
    }

    @PutMapping("/offers/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody ProductUpdate product) {
        productService.update(id, product);

        return new ResponseEntity<>("Product has been updated", HttpStatus.OK);
    }

    @PatchMapping("/offers/{id}")
    public ResponseEntity<String> updatePrice(@PathVariable int id, @RequestBody double newPrice) {
        productService.updatePrice(id, newPrice);
        return new ResponseEntity<>("Product price has been updated", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        productService.delete(id);

        return new ResponseEntity<>("Product has been deleted", HttpStatus.NO_CONTENT);
    }
}
