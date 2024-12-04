package com.food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.model.Feedback;
import com.food.model.MenuItem;
import com.food.model.MenuItemFeedback;

@Repository
public interface MenuItemFeedbackRepository extends JpaRepository<MenuItemFeedback,Long> {
    List<MenuItemFeedback> findByFeedback(Feedback feedback);

    List<MenuItemFeedback> findByMenuItem(MenuItem menuItem);
}
