package com.example.service.impl;

import com.example.repository.OrderDetailsRepository;
import com.example.repository.ProductRepository;
import com.example.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrderServiceImplTest {
    @Mock
    private AuthService authService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderDetailsRepository orderDetailsRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void placeOrder() {

    }
}