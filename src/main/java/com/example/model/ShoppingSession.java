package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "shopping_session")
public class ShoppingSession extends BaseEntity {

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_price")
    private double totalPrice;

}
