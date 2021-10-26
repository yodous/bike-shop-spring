package com.example.service;

import com.example.model.CartItem;
import com.example.model.User;

public interface ShoppingSessionService {
    CartItem add(int userId, int productId);
    void remove(int userId, int productId);
}
