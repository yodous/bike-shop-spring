package com.example.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "shopping_session")
public class ShoppingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;
}
