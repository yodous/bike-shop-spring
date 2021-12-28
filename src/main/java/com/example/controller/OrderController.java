package com.example.controller;

import com.example.dto.OrderDetailsRepresentation;
import com.example.dto.OrderRequest;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private static final String ORDER_PLACED_MESSAGE = "Your order is pending for payment.";

    @GetMapping
    public ResponseEntity<List<OrderDetailsRepresentation>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailsRepresentation> getDetailsById(@PathVariable int id) {
        throw new RuntimeException("not implemented yet");
    }

//    @PostMapping
//    public ResponseEntity<String> orderOne(@RequestBody OrderOneItemRequest request, String paymentType) {
//        orderService.orderProduct(request);
//        return new ResponseEntity<>(ORDER_PLACED_MESSAGE, HttpStatus.CREATED);
//    }

    @PostMapping//("/cart")
    public ResponseEntity<String> orderSelectedFromCart(@RequestBody OrderRequest request)  {
        orderService.orderCartItems(request);
        return new ResponseEntity<>(ORDER_PLACED_MESSAGE, HttpStatus.CREATED);
    }

    @PostMapping("/cart/all")
    public ResponseEntity<String> orderAllItemsFromCart(@RequestBody String paymentType) {
        System.out.println("Payment type: " + paymentType);
        orderService.orderCart(paymentType);
        return new ResponseEntity<>(ORDER_PLACED_MESSAGE, HttpStatus.CREATED);
    }
}
