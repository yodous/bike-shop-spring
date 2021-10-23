package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderDetails details;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
