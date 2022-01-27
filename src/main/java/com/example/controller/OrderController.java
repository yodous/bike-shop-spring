package com.example.controller;

import com.example.dto.OrderRequest;
import com.example.dto.OrderViewResponse;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private static final String ORDER_PLACED_MESSAGE = "Your order is pending for payment.";

    @GetMapping
    public ResponseEntity<OrderViewResponse> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PostMapping
    public ResponseEntity<String> orderSelectedFromCart(@RequestBody OrderRequest request)  {
        orderService.orderCartItems(request);
        return new ResponseEntity<>(ORDER_PLACED_MESSAGE, HttpStatus.CREATED);
    }

}
