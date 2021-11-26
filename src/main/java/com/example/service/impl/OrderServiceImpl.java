package com.example.service.impl;

import com.example.dto.OrderRequest;
import com.example.mapper.OrderMapper;
import com.example.model.OrderItem;
import com.example.repository.OrderItemRepository;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    @Override
    public void add(OrderRequest orderRequest) {
        OrderItem orderItem = orderMapper.mapOrderRequestToOrderItem(orderRequest);
        orderItemRepository.save(orderItem);
    }


}
