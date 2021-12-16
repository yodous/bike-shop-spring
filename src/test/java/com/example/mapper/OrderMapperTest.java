package com.example.mapper;

import com.example.dto.OrderItemRepresentation;
import com.example.model.*;
import com.example.model.enums.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static com.example.model.enums.PaymentStatus.WAITING_FOR_PAYMENT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderMapperTest {
    @Autowired
    private OrderMapper orderMapper;

    private User user;
    private OrderDetails orderDetails;
    private Product product;
    private OrderItem orderItem;

    @BeforeEach
    void setup() {
        user = new User("test_user", null, null, null, null, null, null, null);
        orderDetails = new OrderDetails(user, new PaymentDetails(null, null, WAITING_FOR_PAYMENT));
        product = new Product("product0", "desc0", ProductCategory.FASHION, 1.23);
        orderItem = new OrderItem(orderDetails, product, 1);
    }

    @Test
    void testMapOrderItemToRepresentation() {
        orderItem.setCreatedAt(Instant.now());

        OrderItemRepresentation actual = orderMapper.mapOrderItemToRepresentation(orderItem);

        assertThat(actual.getProductName()).isEqualTo("product0");
        assertThat(actual.getTotalPrice()).isEqualTo(1.23);
        assertThat(actual.getQuantity()).isEqualTo(1);
    }

}