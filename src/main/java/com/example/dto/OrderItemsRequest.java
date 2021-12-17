package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsRequest {
    private List<Integer> ids; // map: k=productid, v=quantity
    private String paymentType;
}
