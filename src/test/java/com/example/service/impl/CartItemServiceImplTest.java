package com.example.service.impl;

import com.example.model.CartItem;
import com.example.model.Product;
import com.example.model.ProductCategory;
import com.example.model.ShoppingSession;
import com.example.repository.CartItemRepository;
import com.example.repository.ProductRepository;
import com.example.repository.ShoppingSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ShoppingSessionServiceImplTest {
    @Mock
    ShoppingSessionRepository sessionRepository;
    @Mock
    CartItemRepository cartItemRepository;
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ShoppingSessionServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void add_ProductToEmptyShoppingSession_ThenSaveNewCartItem() {
        ShoppingSession cart = new ShoppingSession();
        Product product = new Product("test product", "test description", ProductCategory.ELECTRONICS, 1.23);
        CartItem cartItem = new CartItem(cart, product, 1);
        when(sessionRepository.findByUserId(anyInt())).thenReturn(Optional.of(cart));
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(cartItemRepository.save(any())).thenReturn(cartItem);

        assert cart.getTotalPrice() == 0;
        CartItem actualCartItem = service.add(1, 1);

        assertThat(actualCartItem.getProduct()).isEqualTo(product);
        assertThat(actualCartItem.getSession()).isEqualTo(cart);
    }

    @Test
    void remove_FromNotEmptyShoppingSession_ThenOk() {
        ShoppingSession cart = new ShoppingSession();
        Product product = new Product("test product", "test description", ProductCategory.ELECTRONICS, 1.23);
        CartItem cartItem = new CartItem(cart, product, 1);
        when(sessionRepository.findByUserId(anyInt())).thenReturn(Optional.of(cart));
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        when(cartItemRepository.findBySessionAndProductId(any(), anyInt())).thenReturn(Optional.of(cartItem));

        service.remove(1, 1);

        verify(cartItemRepository).delete(cartItem);
    }
}