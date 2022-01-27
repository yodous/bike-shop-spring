package com.example.service;

import com.example.dto.OrderDetailsRepresentation;
import com.example.dto.OrderRequest;
import com.example.dto.OrderViewResponse;

public interface OrderService {
    void orderCartItems(OrderRequest request);

    OrderViewResponse getAll();

    OrderDetailsRepresentation getById(int id);
}
