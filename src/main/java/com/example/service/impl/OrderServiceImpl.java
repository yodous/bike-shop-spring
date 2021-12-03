package com.example.service.impl;

import com.example.dto.FullOrderRequest;
import com.example.dto.OrderItemRepresentation;
import com.example.dto.OrderRequest;
import com.example.mapper.OrderMapper;
import com.example.model.OrderDetails;
import com.example.model.OrderItem;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.OrderDetailsRepository;
import com.example.repository.OrderItemRepository;
import com.example.repository.ProductRepository;
import com.example.service.AuthService;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailsServiceImpl implements OrderService {
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final AuthService authService;
    private final OrderMapper orderMapper;
//    private final MessageSender messageSender; // send order info to user email

    @Override
    public void add(OrderRequest orderRequest) {
        OrderItem orderItem = orderMapper.mapOrderRequestToOrderItem(orderRequest);
        orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItemRepresentation> get() {
        User currentUser = authService.getCurrentUser();

        return orderItemRepository.findAllByOrderUser(currentUser)
                .stream().map(orderMapper::mapSourceToRepresentation)
                .collect(Collectors.toList());
    }

    @Override
    public void placeOrder(FullOrderRequest request) {
        User currentUser = authService.getCurrentUser();
        Map<Integer, Integer> productIdsWithQuantities = request.getProductsIdWithQuantities();
        List<Product> products = productRepository.findAllById(productIdsWithQuantities.keySet());

        OrderDetails orderDetails = new OrderDetails(currentUser, 0);

        LinkedList<OrderItem> orderItems = new LinkedList<>();

        for (Product product : products)
            for (int quantity : productIdsWithQuantities.values()) {
                orderDetails.setTotalPrice(orderDetails.getTotalPrice() + product.getPrice() * quantity);
                orderItems.add(new OrderItem(orderDetails, product, quantity));
            }

        orderDetailsRepository.save(orderDetails);
    }
}
