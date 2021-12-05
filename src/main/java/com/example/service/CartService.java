package com.example.service;

import com.example.dto.CartRepresentation;

public interface CartService {
    CartRepresentation get();

    void saveCartItem(int productId, int quantity);

    void deleteCartItem(int productId, int quantity);

    void deleteAllCartItems();
}
