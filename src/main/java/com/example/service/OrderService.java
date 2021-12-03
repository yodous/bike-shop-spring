package com.example.service;

import com.example.dto.OrderItemRepresentation;
import com.example.dto.OrderItemRequest;

import java.util.List;

public interface OrderService {
    void orderOne(OrderItemRequest orderItemRequest);

    void orderAllFromCart();

    void orderSelectedItemsFromCart(List<Integer> ids);

    List<OrderItemRepresentation> getAll();

}
