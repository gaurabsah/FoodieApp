package com.food.repository;

import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.food.model.Restaurant;
import com.food.model.RestaurantPartner;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    Optional<Restaurant> findByRestaurantPartner(RestaurantPartner restaurantPartner);

    @Query(value = "SELECT r.*, ST_Distance(r.address, :customerLocation) AS distance " +
            "FROM restaurant r " +
            "WHERE r.is_open = true AND ST_DWithin(r.address, :customerLocation, 10000) " +
            "ORDER BY distance ASC, r.rating DESC ", nativeQuery = true)
    Page<Restaurant> findNearByRestaurants(Point customerLocation, PageRequest pageRequest);
}
