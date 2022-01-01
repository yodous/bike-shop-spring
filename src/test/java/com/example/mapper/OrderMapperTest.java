package com.example.mapper;

import com.example.model.OrderDetails;
import com.example.model.OrderItem;
import com.example.model.Product;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderMapperTest {
    @Autowired
    private OrderMapperImpl orderMapper;

    private User user;
    private OrderDetails orderDetails;
    private Product product;
    private OrderItem orderItem;
}