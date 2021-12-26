package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsRequest {
    private Map<Integer, Integer> itemIdsWithQuantity;
    private String paymentType;
}
