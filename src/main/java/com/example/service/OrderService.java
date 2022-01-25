package com.example.service;

import com.example.dto.OrderDetailsRepresentation;
import com.example.dto.OrderDetailsResponse;
import com.example.dto.OrderRequest;

public interface OrderService {
    void orderCartItems(OrderRequest request);

    OrderDetailsResponse getAll();

}
