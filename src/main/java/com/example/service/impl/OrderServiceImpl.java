package com.example.service.impl;

import com.example.dto.OrderDetailsRepresentation;
import com.example.dto.OrderItemRequest;
import com.example.exception.ProductNotFoundException;
import com.example.mapper.OrderMapper;
import com.example.model.*;
import com.example.model.enums.PaymentStatus;
import com.example.model.enums.PaymentType;
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
    private final PaymentDetailsRepository paymentDetailsRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AuthService authService;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDetailsRepresentation> getAll() {
        User currentUser = authService.getCurrentUser();

        return orderDetailsRepository.findAllByUser(currentUser)
                .stream().map(orderMapper::mapDetailsToRepresentation)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailsRepresentation getById(int id) {
        User currentUser = authService.getCurrentUser();
        return orderDetailsRepository.findOrderDetailsByUserAndId(currentUser, id)
                .map(orderMapper::mapDetailsToRepresentation).orElseThrow(
                        () -> new RuntimeException("Could not find order details with id = " + id)
                );
    }

    @Override
    @Transactional
    public void orderProduct(OrderItemRequest request) {
        User currentUser = authService.getCurrentUser();
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));


        OrderDetails orderDetails = orderDetailsRepository.save(new OrderDetails(
                currentUser, product.getPrice() * request.getQuantity()));

        PaymentDetails paymentDetails = new PaymentDetails(orderDetails, PaymentType.valueOf(request.getPaymentType()), PaymentStatus.PENDING);
        paymentDetailsRepository.save(paymentDetails);

        orderItemRepository.save(new OrderItem(orderDetails, product, request.getQuantity()));
    }

    @Override
    @Transactional
    public void orderCartItems(List<Integer> cartItemsIds) {
        User currentUser = authService.getCurrentUser();
        Cart cart = cartRepository.findByUser(currentUser).orElseThrow();
        List<CartItem> cartItems = cartItemRepository.findAllById(cartItemsIds);

        double totalPrice = 0;
        OrderDetails orderDetails = orderDetailsRepository.save(new OrderDetails(currentUser));

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cartItems) {
            int productId = cartItem.getProduct().getId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));
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
