package com.food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.model.Menu;
import com.food.model.Restaurant;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {

    List<Menu> findByRestaurant(Restaurant restaurant);
}
