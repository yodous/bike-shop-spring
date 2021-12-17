package com.example.mapper;

import com.example.dto.CartItemRepresentation;
import com.example.dto.CartRepresentation;
import com.example.model.Cart;
import com.example.model.CartItem;
import com.example.model.Product;
import com.example.model.User;
import com.example.model.enums.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CartMapperTest {
    @Autowired
    private CartMapper cartMapper;

    private Cart cart;
    private Product product;
    private CartItem cartItem;

    @BeforeEach
    void setup() {
        product = new Product("product0", "desc0", ProductCategory.ELECTRONICS, 1.23);
        cartItem = new CartItem(cart, product, 2);
        cart = new Cart(new User(), 10, List.of(cartItem, cartItem));
    }

    @Test
    void testMapItemSourceToRepresentation() {
        CartItemRepresentation expected = new CartItemRepresentation(1,
                product.getName(), product.getPrice(), 2, 2.46);

        CartItemRepresentation actual = cartMapper.mapItemSourceToRepresentation(cartItem);

        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual.getTotalPrice()).isEqualTo(expected.getTotalPrice());
    }

    @Test
    void testMapCartToRepresentation() {
        CartRepresentation expected = new CartRepresentation(
                List.of(new CartItemRepresentation(), new CartItemRepresentation()), 10);
        CartRepresentation actual = cartMapper.mapCartToRepresentation(cart);

        assertThat(actual.getItems().size()).isEqualTo(expected.getItems().size());
        assertThat(actual.getTotalPrice()).isEqualTo(expected.getTotalPrice());
    }

    @Test
    void testCalculateTotalPrice() {
        double expected = 2.46;
        double actual = cartMapper.calculateTotalPrice(cartItem);

        assertThat(actual).isEqualTo(expected);
    }
}