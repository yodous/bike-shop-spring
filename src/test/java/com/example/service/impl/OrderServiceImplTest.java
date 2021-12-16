package com.example.service.impl;

import com.example.dto.OrderItemsRequest;
import com.example.mapper.OrderMapper;
import com.example.model.*;
import com.example.model.enums.ProductCategory;
import com.example.repository.*;
import com.example.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

class OrderServiceImplTest {
    @Mock
    private OrderDetailsRepository orderDetailsRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private AuthService authService;
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderDetails orderDetails;
    private Cart cart;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        User user = new User();
        cart = new Cart(user, 0, null);
        Product product1 = new Product("prod1", "desc1", ProductCategory.FASHION, 1);
        CartItem cartItem1 = new CartItem(cart, product1, 1);

        given(authService.getCurrentUser()).willReturn(user);
        given(cartRepository.findByUser(user)).willReturn(Optional.of(cart));
        given(cartItemRepository.findAllById(anyList())).willReturn(List.of(cartItem1));
        orderDetails = new OrderDetails(user, 0);
        given(orderDetailsRepository.save(any())).willReturn(orderDetails);
        given(productRepository.findById(anyInt())).willReturn(Optional.of(product1));
    }

    @Test
    void testOrderCartItems() {
        double orderPriceBefore = orderDetails.getTotalPrice();
        double cartPriceBefore = cart.getTotalPrice();

        orderService.orderCartItems(new OrderItemsRequest(List.of(1), "TRANSFER"));

        double orderPriceAfter = orderDetails.getTotalPrice();
        double cartPriceAfter = cart.getTotalPrice();

        assertThat(orderPriceBefore).isLessThan(orderPriceAfter);
        assertThat(cartPriceBefore).isGreaterThan(cartPriceAfter);
    }

}