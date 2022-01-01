package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRepresentation {
    private int productId;
    @JsonProperty("name")
    private String productName;
    private int quantity;
    @JsonProperty("price")
    private double productPrice;
}
