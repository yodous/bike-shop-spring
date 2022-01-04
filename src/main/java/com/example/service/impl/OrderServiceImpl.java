package com.example.service.impl;

import com.example.dto.OrderDetailsRepresentation;
import com.example.dto.OrderItemRequest;
import com.example.dto.OrderRequest;
import com.example.mapper.OrderMapper;
import com.example.model.*;
import com.example.model.embeddable.Address;
import com.example.model.embeddable.BillingAddress;
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
    public void orderCartItems(OrderRequest request) {
        User currentUser = authService.getCurrentUser();
        Cart cart = currentUser.getCart();
        List<OrderItemRequest> items = request.getItems();
        if (items == null)
            throw new RuntimeException("no product selected");
        if (request.getPaymentType() == null)
            throw new RuntimeException("Payment Type not selected");

        OrderDetails orderDetails = new OrderDetails(currentUser);

        Address address = new Address(request.getCity(), request.getStreet(), request.getPostalCode());
        orderDetails.setBillingAddress(new BillingAddress(request.getFullName(), request.getEmail(), address));

        PaymentDetails paymentDetails = new PaymentDetails(
                PaymentType.valueOf(request.getPaymentType()), PaymentStatus.PENDING);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest item : items) {
            Product product = productRepository
                    .findById(item.getProductId()).orElseThrow(
                            () -> new RuntimeException("Could not find product with id = " + item.getProductId())
                    );
            cartItemRepository.deleteByCartIdAndProductId(cart.getId(), product.getId());
            int quantity = item.getQuantity();
            orderItems.add(new OrderItem(orderDetails, product, quantity));
            orderDetails.setTotalPrice(orderDetails.getTotalPrice() + product.getPrice() * quantity);
        }

        cart.setTotalPrice(cart.getTotalPrice() - orderDetails.getTotalPrice());
        cartRepository.save(cart);

        paymentDetails.setOrderDetails(orderDetailsRepository.save(orderDetails));
        paymentDetailsRepository.save(paymentDetails);

        orderItemRepository.saveAll(orderItems);
    }

}
