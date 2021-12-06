package com.example.controller;

import com.example.dto.OrderItemRepresentation;
import com.example.dto.OrderItemRequest;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private static final String ORDER_PLACED_MESSAGE = "Your order is pending for payment.";

    @GetMapping
    public ResponseEntity<List<OrderItemRepresentation>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PostMapping
    public ResponseEntity<String> orderOne(@RequestBody OrderItemRequest request) {
        orderService.orderProduct(request);
        return new ResponseEntity<>(ORDER_PLACED_MESSAGE, HttpStatus.CREATED);
    }

    @PostMapping("/cart")
    public ResponseEntity<String> orderSelectedFromCart(@RequestBody List<Integer> productsIds) {
        orderService.orderCartItems(productsIds);
        return new ResponseEntity<>(ORDER_PLACED_MESSAGE, HttpStatus.CREATED);
    }

    @PostMapping("/cart/all")
    public ResponseEntity<String> orderAllItemsFromCart() {
        orderService.orderCart();
        return new ResponseEntity<>(ORDER_PLACED_MESSAGE, HttpStatus.CREATED);
    }
}
