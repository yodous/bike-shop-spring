package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRepresentation {
    private int id;
    private String imgUrl;
    private String name;
    private double price;
    private int quantity;
    private double totalPrice;
}