package com.example.model;

import com.example.model.abstracts.BaseEntity;
import com.example.model.embeddable.BillingAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_details")
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

    @Embedded
    private BillingAddress billingAddress;

    public OrderDetails(User user) {
        this.user = user;
    }

}
