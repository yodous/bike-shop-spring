package com.example.service;

import com.example.dto.OrderDetailsRepresentation;
import com.example.dto.OrderItemRequest;

import java.util.List;

public interface OrderService {
    void orderProduct(OrderItemRequest orderItemRequest);

    void orderCart();

    void orderCartItems(List<Integer> ids);

    List<OrderDetailsRepresentation> getAll();

    OrderDetailsRepresentation getById(int id);
}
