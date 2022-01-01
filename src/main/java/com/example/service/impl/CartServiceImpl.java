package com.example.service.impl;

import com.example.dto.CartItemRepresentation;
import com.example.dto.CartRepresentation;
import com.example.exception.ProductNotFoundException;
import com.example.mapper.CartMapper;
import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartItemRepository;
import com.example.repository.CartRepository;
import com.example.repository.ProductRepository;
import com.example.service.AuthService;
import com.example.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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

    @Override
    public CartItemRepresentation getItemByProductId(int productId) {
        return this.cartItemRepository.findByProductId(productId)
                .map(cartMapper::mapItemSourceToRepresentation)
                .orElseThrow(() -> new ProductNotFoundException(productId));
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
        Optional<CartItem> cartProduct = cartItemRepository.findByCartAndProductId(cart, productId);
        if (cartProduct.isPresent()) {
            CartItem cartItem = cartProduct.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItemRepository.save(cartItem);
        } else
            cartItemRepository.save(new CartItem(cart, product, quantity));

        cart.setTotalPrice(cart.getTotalPrice() + (quantity * product.getPrice()));
    }

    @Override
    @Transactional
    public void deleteCartItem(int productId, int quantity) {
        CartItem cartItem = cartItemRepository.findByProductId(productId).orElseThrow(
                () -> new RuntimeException("No cart item with id=" + productId));

        Cart cart = findCartByCurrentUser();
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getProduct().getPrice() * quantity);

        if (cartItem.getQuantity() < quantity)
            throw new IllegalArgumentException("Cart item quantity lower then quantity to delete");
        if (cartItem.getQuantity() == 1)
            cartItemRepository.delete(cartItem);
        else
            cartItem.setQuantity(cartItem.getQuantity() - quantity);

        cartRepository.save(cart);
    }


    @Override
    @Transactional
    public void deleteAllCartItems() {
        Cart cart = findCartByCurrentUser();
        cart.setTotalPrice(0);

        cartItemRepository.deleteAllByCart(cart);
    }
}