package com.example.mapper;

import com.example.dto.OrderItemRepresentation;
import com.example.model.OrderItem;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    public OrderItemRepresentation mapOrderItemToRepresentation(OrderItem orderItem) {
        return new OrderItemRepresentation(orderItem.getOrder().getUser().getUsername(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getProduct().getPrice(),
                LocalDateTime.ofInstant(orderItem.getCreatedAt(), ZoneId.systemDefault())
                        .truncatedTo(ChronoUnit.MINUTES),
                String.valueOf(orderItem.getOrder().getPaymentDetails().getStatus()),
                orderItem.getProduct().getPrice() * orderItem.getQuantity());
    }

}
