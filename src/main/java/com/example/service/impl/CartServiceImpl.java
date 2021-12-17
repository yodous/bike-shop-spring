package com.example.service.impl;

import com.example.dto.CartRepresentation;
import com.example.exception.ProductNotFoundException;
import com.example.mapper.CartMapper;
import com.example.model.CartItem;
import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartItemRepository;
import com.example.repository.ProductRepository;
import com.example.repository.CartRepository;
import com.example.service.AuthService;
import com.example.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final AuthService authService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Override
    public CartRepresentation get() {
        Cart cart = findCartByCurrentUser();
        return cartMapper.mapCartToRepresentation(cart);
    }

    private Cart findCartByCurrentUser() {
        User currentUser = authService.getCurrentUser();
        return cartRepository.findByUser(currentUser)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Could not find cart with userId=" + currentUser.getId()));
    }

    @Override
    @Transactional
    public void saveCartItem(int productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException(productId));

        Cart cart = findCartByCurrentUser();
        cart.setTotalPrice(cart.getTotalPrice() + (quantity * product.getPrice()));

        cartItemRepository.save(new CartItem(cart, product, quantity));
    }

    @Override
    @Transactional
    public void deleteCartItem(int itemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(itemId).orElseThrow(
                () -> new RuntimeException("No cart item with id=" + itemId));

        Cart cart = findCartByCurrentUser();
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProduct().getPrice());

        cartItemRepository.delete(cartItem);
        log.info("cart price: " + cart.getTotalPrice());

//        cartRepository.save(cart);
    }


    @Override
    @Transactional
    public void deleteAllCartItems() {
        Cart cart = findCartByCurrentUser();
        cart.setTotalPrice(0);

        cartItemRepository.deleteAllByCart(cart);
    }
}