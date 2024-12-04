package com.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.model.MenuItem;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
}
