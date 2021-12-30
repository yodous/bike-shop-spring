package com.example.model;

import com.example.model.abstracts.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_details")
@Setter
@Getter
@NoArgsConstructor
public class OrderDetails extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "order")
    private List<OrderItem> orderItems;

    @Column(name = "total")
    private double totalPrice;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "orderDetails")
    private PaymentDetails paymentDetails;

    public OrderDetails(User user) {
        this.user = user;
    }

}
