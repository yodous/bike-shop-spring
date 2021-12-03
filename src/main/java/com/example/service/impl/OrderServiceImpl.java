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
    @Transactional
    public void orderOne(OrderItemRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));

        OrderDetails orderDetails = orderDetailsRepository.save(
                new OrderDetails(authService.getCurrentUser(), product.getPrice() * request.getQuantity()));

        orderItemRepository.save(new OrderItem(orderDetails, product, request.getQuantity()));
    }

    @Override
    public void orderSelectedItemsFromCart(List<Integer> ids) {
        User currentUser = authService.getCurrentUser();
        List<CartItem> cartItems = cartItemRepository.findAllById(ids); //todo: shouldn't it be a set?
        OrderDetails orderDetails = orderDetailsRepository.save(new OrderDetails(currentUser, 0));

        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProduct().getId()).orElseThrow();
            orderItems.add(new OrderItem(orderDetails, product, cartItem.getQuantity()));
            totalPrice += product.getPrice() * cartItem.getQuantity();
        }
        log.info("total price before = " + orderDetails.getTotalPrice());
        orderDetails.setTotalPrice(totalPrice);
        log.info("total price after = " + orderDetails.getTotalPrice());
    }

    @Override
    @Transactional
    public void orderAllFromCart() {
        User currentUser = authService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(currentUser.getId()).orElseThrow(
                () -> new RuntimeException("Could not find cart with userId=" + currentUser.getId()));
        List<Integer> cartItemsIds = cartItemRepository.findAllByCart(cart)
                .stream().map(CartItem::getId).collect(Collectors.toList());
        orderSelectedItemsFromCart(cartItemsIds);
    }

    @Override
    public List<OrderItemRepresentation> getAll() {
        User currentUser = authService.getCurrentUser();

        return orderItemRepository.findAllByOrderUser(currentUser)
                .stream().map(orderMapper::mapSourceToRepresentation)
                .collect(Collectors.toList());
    }

}
