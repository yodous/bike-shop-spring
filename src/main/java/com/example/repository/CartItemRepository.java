package com.example.repository;

import com.example.model.Cart;
import com.example.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartAndProductId(Cart cart, int productId);

    Optional<CartItem> findByProductId(int productId);

    @Query("select c.id from CartItem c where c.cart=:cart")
    List<CartItem> findAllByCart(@Param("cart") Cart cart);

    void deleteAllByCart(Cart cart);

    @Transactional
    @Modifying
    @Query("delete from CartItem c where c.cart.id = :cart and c.product.id = :product")
    void deleteByCartIdAndProductId(@Param("cart") int cartId, @Param("product") int productId);
}
