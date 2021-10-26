package com.example.repository;

import com.example.model.CartItem;
import com.example.model.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findBySessionAndProductId(ShoppingSession session, int productId);
}
