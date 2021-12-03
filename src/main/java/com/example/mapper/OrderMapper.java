package com.example.mapper;

import com.example.dto.OrderItemRepresentation;
import com.example.dto.OrderRequest;
import com.example.exception.ProductNotFoundException;
import com.example.model.*;
import com.example.model.enums.PaymentStatus;
import com.example.model.enums.PaymentType;
import com.example.repository.OrderDetailsRepository;
import com.example.repository.PaymentDetailsRepository;
import com.example.repository.ProductRepository;
import com.example.service.AuthService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    @Autowired
    private AuthService authService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
//    @Autowired
//    private PaymentDetailsRepository paymentDetailsRepository;

    public OrderItemRepresentation mapSourceToRepresentation(OrderItem orderItem) {
        return new OrderItemRepresentation(orderItem.getOrder().getUser().getUsername(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getProduct().getPrice(),
                LocalDateTime.ofInstant(orderItem.getCreatedAt(), ZoneId.systemDefault())
                        .truncatedTo(ChronoUnit.MINUTES),
                String.valueOf(orderItem.getOrder().getPaymentDetails().getStatus()),
                orderItem.getProduct().getPrice() * orderItem.getQuantity());
    }

    @Transactional
    public OrderItem mapOrderRequestToOrderItem(OrderRequest request) {
        User currentUser = authService.getCurrentUser();
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(request.getProductId()));

        OrderDetails orderDetails = orderDetailsRepository.save(new OrderDetails(
                currentUser, product.getPrice() * request.getQuantity()));

//        PaymentDetails paymentDetails = new PaymentDetails(orderDetails,
//                getPaymentType(request.getPaymentType()), PaymentStatus.WAITING_FOR_PAYMENT);
//        paymentDetailsRepository.save(paymentDetails);

        return new OrderItem(orderDetails, product, request.getQuantity());
    }
//
//    private PaymentType getPaymentType(String payment) {
//        if (Arrays.stream(PaymentType.values()).noneMatch(v -> (v.getValue()).equals(payment.toLowerCase())))
//            throw new IllegalArgumentException("invalid payment type");
//
//        return PaymentType.valueOf(payment.toUpperCase());
//    }

}
