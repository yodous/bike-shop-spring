package com.example.repository;

import com.example.model.Cart;
import com.example.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    void deleteAllByCart(Cart cart);

    @Query("select c.id from CartItem c where c.cart=:cart")
    List<Integer> findAllByCart(@Param("cart") Cart cart);
}
