package com.example.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total")
    private double totalPrice;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private PaymentDetails paymentDetails;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;
}
