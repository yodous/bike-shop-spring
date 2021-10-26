package com.example.service;

import com.example.model.User;

public interface OrderService {
    void orderProductById(int userId, int productId);
    void orderAllProductsFromCart(User user, int cartId);
}
