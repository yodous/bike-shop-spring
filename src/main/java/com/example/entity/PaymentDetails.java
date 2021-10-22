package com.example.entity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "payment_details")
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderDetails orderDetails;

    @Column(name = "amount")
    private int amount;

    @Column(name = "provider")
    private String provider;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;
}
