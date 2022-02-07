package com.example.service.impl;

import com.example.dto.OrderItemRequest;
import com.example.dto.OrderRequest;
import com.example.mapper.OrderMapper;
import com.example.model.Cart;
import com.example.model.OrderDetails;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.enums.PaymentType;
import com.example.model.enums.ProductCategory;
import com.example.repository.*;
import com.example.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private Product product1;
    private Product product2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        User user = new User();
        user.setUsername("testUser");
        cart = new Cart(user, 0, null);
        product1 = new Product("bike1", "road bike", ProductCategory.ROAD, 1000);
        product2 = new Product("bike2", "mountain bike", ProductCategory.MOUNTAIN, 2000);
        orderDetails = new OrderDetails(user);
//        CartItem cartItem1 = new CartItem(cart, product1, 1);

        given(authService.getCurrentUser()).willReturn(user);
        given(cartRepository.findByUser(user)).willReturn(Optional.of(cart));
//        given(orderDetailsRepository.save(any())).willReturn(orderDetails);
        given(productRepository.findById(1)).willReturn(Optional.of(product1));
        given(productRepository.findById(2)).willReturn(Optional.of(product2));
    }

    @Test
    void orderCartItems_itemListIsNull_shouldFail() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setPaymentType(PaymentType.TRANSFER.getValue());
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.orderCartItems(orderRequest));

        String expectedMessage = "Products not selected";
        String actualMessage = exception.getMessage();
        assertThat(actualMessage).containsIgnoringCase(expectedMessage);
    }

    @Test
    void orderCartItems_itemListIsEmpty_shouldFail() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setPaymentType(PaymentType.TRANSFER.getValue());
        orderRequest.setItems(Collections.emptyList());
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.orderCartItems(orderRequest));

        String expectedMessage = "Products not selected";
        String actualMessage = exception.getMessage();
        assertThat(actualMessage).containsIgnoringCase(expectedMessage);
    }

    @Test
    void orderCartItems_paymentTypeIsNull_shouldFail() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItems(List.of(new OrderItemRequest()));
        Exception exception = assertThrows(RuntimeException.class,
                () -> orderService.orderCartItems(orderRequest));

        String expectedMessage = "Payment type must not be null";
        String actualMessage = exception.getMessage();
        assertThat(actualMessage).containsIgnoringCase(expectedMessage);
    }

    @Test
    void orderCartItems_paymentTypeEmpty_shouldFail() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItems(List.of(new OrderItemRequest()));
        orderRequest.setPaymentType("");
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.orderCartItems(orderRequest));

        String expectedMessage = "Invalid payment type";
        String actualMessage = exception.getMessage();
        assertThat(actualMessage).containsIgnoringCase(expectedMessage);
    }

    @Test
    void orderCartItems_shouldSucceed() {
		// TODO ML: some left-overs; to remove?;
//        OrderItemRequest orderItemRequest1 = new OrderItemRequest(1, 1);
//        OrderItemRequest orderItemRequest2 = new OrderItemRequest(2, 2);
//        OrderRequest orderRequest = new OrderRequest(List.of(orderItemRequest1, orderItemRequest2),
//                "fullname","email", "city", "street",
//                "12-345", PaymentType.TRANSFER.getValue());
//        given(cartItemRepository.findById(anyInt())).willReturn(Optional.of(new CartItem(cart, product1, 1)));
//        doNothing().when(cartItemRepository).deleteByCartIdAndProductId(anyInt(), anyInt());
//
//        assertDoesNotThrow(() -> orderService.orderCartItems(orderRequest));
    }

}
