package com.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product extends BaseEntity {

    @NotEmpty(message = "Product name must not be empty")
    @Max(value = 30, message = "Product name must be not longer than 30 characters")
    @Column(name = "name")
    private String name;

    @Min(value = 10, message = "Description must be longer then 10 characters")
    @Max(value = 255, message = "Description must be shorter than 255 characters")
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Min(value = 0, message= "Price cannot be negative")
    @Column(name = "price")
    private double price;

//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "seller_id")
//    private User seller;

    public Product(String name, String description, Category category, double price) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public enum Category {
        SPORT, GARDEN, MOTORIZATION, HEALTH, HOME, TECH
    }
}
