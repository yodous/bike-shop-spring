package com.example.controller;

import com.example.dto.CartItemRepresentation;
import com.example.dto.CartRepresentation;
import com.example.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartRepresentation> get() {
        CartRepresentation cart = cartService.get();
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemRepresentation> getCartItem(@PathVariable int id) {
        return ResponseEntity.ok(cartService.getItemByProductId(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> addProduct(@PathVariable int id) {
        cartService.saveCartItem(id, 1);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
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