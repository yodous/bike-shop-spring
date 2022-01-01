package com.example.mapper;

import com.example.dto.PaymentRepresentation;
import com.example.model.PaymentDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "totalPrice", expression = "java(details.getOrderDetails().getTotalPrice())")
    @Mapping(target = "paymentType", expression = "java(details.getType().getValue())")
    @Mapping(target = "paymentStatus", expression = "java(details.getStatus().getValue())")
    @Mapping(target = "paymentDate", source = "createdAt")
    PaymentRepresentation mapSourceToDto(PaymentDetails details);
}
