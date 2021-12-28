package com.example.service;

import com.example.dto.CartItemRepresentation;
import com.example.dto.CartRepresentation;

public interface CartService {
    CartRepresentation get();

    CartItemRepresentation getItemByProductId(int productId);

    void saveCartItem(int productId, int quantity);

    void deleteCartItem(int productId, int quantity);

    void deleteAllCartItems();
}
