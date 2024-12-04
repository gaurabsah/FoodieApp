package com.food.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.enums.CartStatus;
import com.food.model.Cart;
import com.food.model.Customer;
import com.food.model.Restaurant;

public interface CartRepository extends JpaRepository<Cart, Long> {
	// Method to find a Cart by Customer, Restaurant, and CartStatus
	Optional<Cart> findByCustomerAndRestaurantAndCartStatus(Customer customer, Restaurant restaurant,
			CartStatus cartStatus);
}
