package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
public class CartRepresentation {
    private List<CartItemRepresentation> items;
    private double totalPrice;
}