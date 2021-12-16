package com.example.service;

import com.example.dto.OrderDetailsRepresentation;
import com.example.dto.OrderItemsRequest;
import com.example.dto.OrderOneItemRequest;

import java.util.List;

public interface OrderService {
    void orderProduct(OrderOneItemRequest orderOneItemRequest);

    void orderCart(String paymentType);

    void orderCartItems(OrderItemsRequest request);

    List<OrderDetailsRepresentation> getAll();

    OrderDetailsRepresentation getById(int id);
}
