package com.food.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.model.RestaurantPartner;
import com.food.model.User;

public interface RestaurantPartnerRepository extends JpaRepository<RestaurantPartner,Long> {
    Optional<RestaurantPartner> findByUser(User user);
}
