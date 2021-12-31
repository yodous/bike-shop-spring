package com.example.dto;

import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductView {
    private int id;
    private String name;
    private String category;
    private String imgUrl;
    private double price;
    private String lastModified;
}
