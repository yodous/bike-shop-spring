package com.example.service;

import com.example.dto.OrderDetailsRepresentation;
import com.example.dto.OrderRequest;
import com.example.dto.OrderItemRequest;

import java.util.List;

public interface OrderService {
    void orderCartItems(OrderRequest request);

    List<OrderDetailsRepresentation> getAll();

    OrderDetailsRepresentation getById(int id);
}
