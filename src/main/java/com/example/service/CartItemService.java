package com.example.service;

import com.example.model.CartItem;

public interface CartItemService {
    CartItem add(int userId, int productId);
    void remove(int userId, int productId);
}
