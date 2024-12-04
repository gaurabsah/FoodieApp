package com.food.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.model.Customer;
import com.food.model.User;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUser(User user);
}
