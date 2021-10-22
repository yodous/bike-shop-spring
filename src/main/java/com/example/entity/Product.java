package com.example.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

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

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;

//    @ManyToOne(cascade = CascadeType.REMOVE)
//    @JoinColumn(name = "seller_id")
//    private User seller;

    public enum Category {

    }
}
