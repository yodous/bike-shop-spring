package com.example.controller;

import com.example.dto.CartRepresentation;
import com.example.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping//todo: pagination
    public ResponseEntity<CartRepresentation> get() {
        return ResponseEntity.ok(cartService.get());
    }

    //todo: jwt token expired -> should be 401 but is 200
    @PostMapping//("/{id}")
    public ResponseEntity<String> addProduct(@RequestParam int id,
                                             @RequestParam int quantity) {
        log.info("cartController -> addProduct with id = " + id + ", quantity = " + quantity);
        cartService.saveCartItem(id, quantity);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestParam int id,
                                                @RequestParam int quantity) {
        log.info("id: " + id + ", quantity: " + quantity);

        cartService.deleteCartItem(id, quantity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllProducts() {
        cartService.deleteAllCartItems();
        return new ResponseEntity<>("Products have been removed from cart",
                HttpStatus.NO_CONTENT);
    }

}