package com.example.repository;

import com.example.model.OrderDetails;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    List<OrderDetails> findAllByUser(User user);
    Optional<OrderDetails> findOrderDetailsByUserAndId(User user, int orderId);
}
