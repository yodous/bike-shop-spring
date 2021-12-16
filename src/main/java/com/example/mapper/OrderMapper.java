package com.example.mapper;

import com.example.dto.OrderDetailsRepresentation;
import com.example.dto.OrderItemRepresentation;
import com.example.model.OrderDetails;
import com.example.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    @Mapping(target = "productName", expression = "java(orderItem.getProduct().getName())")
    @Mapping(target = "quantity", expression = "java(orderItem.getQuantity())")
    @Mapping(target = "productPrice", expression = "java(orderItem.getProduct().getPrice())")
    @Mapping(target = "totalPrice", expression = "java(orderItem.getProduct().getPrice() * orderItem.getQuantity())")
    public abstract  OrderItemRepresentation mapOrderItemToRepresentation(OrderItem orderItem);

    public OrderDetailsRepresentation mapDetailsToRepresentation(OrderDetails orderDetails) {
        return new OrderDetailsRepresentation(
                orderDetails.getUser().getUsername(),
                orderDetails.getTotalPrice(),
                orderDetails.getOrderItems().stream()
                        .map(o -> mapOrderItemToRepresentation(o)).collect(Collectors.toList()),
                orderDetails.getPaymentDetails().getType().getValue(),
                orderDetails.getPaymentDetails().getStatus().name(),
                LocalDate.ofInstant(orderDetails.getCreatedAt(), ZoneId.systemDefault())
        );
    }

    ;

}
