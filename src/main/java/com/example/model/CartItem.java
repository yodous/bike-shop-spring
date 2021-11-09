package com.example.model;

import com.example.model.abstracts.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cart_item")
@NoArgsConstructor
public class CartItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "session_id")
    private ShoppingSession session;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    public CartItem(ShoppingSession session, Product product, int quantity) {
        this.session = session;
        this.product = product;
        this.quantity = quantity;
    }
}
