package com.example.service.impl;

import com.example.dto.OrderItemRepresentation;
import com.example.dto.OrderItemRequest;
import com.example.exception.ProductNotFoundException;
import com.example.mapper.OrderMapper;
import com.example.model.*;
import com.example.repository.*;
import com.example.service.AuthService;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AuthService authService;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderItemRepresentation> getAll() {
        User currentUser = authService.getCurrentUser();

        return orderItemRepository.findAllByOrderUser(currentUser)
                .stream().map(orderMapper::mapSourceToRepresentation)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void orderProduct(OrderItemRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));

        OrderDetails orderDetails = orderDetailsRepository.save(
                new OrderDetails(authService.getCurrentUser(), product.getPrice() * request.getQuantity()));

        orderItemRepository.save(new OrderItem(orderDetails, product, request.getQuantity()));
    }

    @Override
    @Transactional
    public void orderCartItems(List<Integer> cartItemsIds) {
        User currentUser = authService.getCurrentUser();
        Cart cart = cartRepository.findByUser(currentUser).orElseThrow();
        List<CartItem> cartItems = cartItemRepository.findAllById(cartItemsIds);

        double totalPrice = 0;
        OrderDetails orderDetails = orderDetailsRepository.save(new OrderDetails(currentUser, totalPrice));

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProduct().getId()).orElseThrow();
            orderItems.add(new OrderItem(orderDetails, product, cartItem.getQuantity()));
            totalPrice += product.getPrice() * cartItem.getQuantity();
        }
        orderDetails.setTotalPrice(totalPrice);
        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteAllById(cartItemsIds);

        cart.setTotalPrice(cart.getTotalPrice() - totalPrice);
    }

    @Override
    @Transactional
    public void orderCart() {
        User currentUser = authService.getCurrentUser();
        Cart cart = cartRepository.findByUser(currentUser).orElseThrow(
                () -> new RuntimeException("Could not find cart with userId=" + currentUser.getId()));
        List<Integer> cartItemsIds = cartItemRepository.findAllByCart(cart);

        orderCartItems(cartItemsIds);
    }

}
