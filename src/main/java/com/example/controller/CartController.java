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

    @GetMapping
    public ResponseEntity<CartRepresentation> get() {
        return ResponseEntity.ok(cartService.get());
    }

    //todo: jwt token expired -> should be 401 but is 200
    // inform user about need to authenticated or token has expired
    @PostMapping("/{id}")
    public ResponseEntity<Void> addProduct(@PathVariable int id) {
        log.info("cartController -> addProduct with id = " + id + ", quantity = " + 1);
        cartService.saveCartItem(id, 1);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        log.info("id: " + id);

        cartService.deleteCartItem(id, 1);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllProducts() {
        cartService.deleteAllCartItems();
        return new ResponseEntity<>("Products have been removed from cart",
                HttpStatus.NO_CONTENT);
    }

}