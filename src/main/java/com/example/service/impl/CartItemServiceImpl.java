package com.example.service.impl;

import com.example.exception.ProductNotFoundException;
import com.example.model.CartItem;
import com.example.model.Product;
import com.example.model.ShoppingSession;
import com.example.repository.CartItemRepository;
import com.example.repository.ProductRepository;
import com.example.repository.ShoppingSessionRepository;
import com.example.service.ShoppingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingSessionServiceImpl implements ShoppingSessionService {
    private final ShoppingSessionRepository sessionRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public CartItem add(int userId, int productId) {
        ShoppingSession cart = sessionRepository.findByUserId(userId).orElseThrow(
                () -> new RuntimeException(String.format("User with id %d not found", userId)));
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException(productId));
        Optional<CartItem> cartItemOpt = cartItemRepository.findBySessionAndProductId(cart, productId);

        if (cartItemOpt.isEmpty()) // if product is not in cart yet save new cart item else increment quantity
            return cartItemRepository.save(new CartItem(cart, product, 1));

        CartItem cartItem = cartItemOpt.get();
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void remove(int userId, int productId) {
        ShoppingSession cart = sessionRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException(
                        "Could not find shopping session with userId=" + userId));
        CartItem cartItem = cartItemRepository.findBySessionAndProductId(cart, productId)
                .orElseThrow(ProductNotFoundException::new);

        cartItemRepository.delete(cartItem);
    }
}
