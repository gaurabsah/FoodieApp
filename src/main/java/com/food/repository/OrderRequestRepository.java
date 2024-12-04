package com.food.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.model.Cart;
import com.food.model.OrderRequest;

public interface OrderRequestRepository extends JpaRepository<OrderRequest,Long> {
    Optional<OrderRequest> findByCart(Cart cart);
}
