package com.food.service;

import java.util.List;

import com.food.model.Menu;
import com.food.model.Restaurant;

public interface MenuService {
    Menu createNewMenuForRestaurant(Menu menu, Restaurant restaurant);
    Menu updateMenuActiveStatus(Boolean status,Menu menu);
    Menu getMenuById(Long menuId);
    List<Menu> getMenusByRestaurant(Restaurant restaurant);
    Menu updateMenuOfRestaurant(Menu menu, Long menuId);
}
