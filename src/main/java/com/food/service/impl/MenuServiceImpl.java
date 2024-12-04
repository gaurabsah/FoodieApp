package com.food.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.exception.ResourceNotFoundException;
import com.food.model.Menu;
import com.food.model.Restaurant;
import com.food.repository.MenuRepository;
import com.food.service.MenuService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    @Override
    public Menu createNewMenuForRestaurant(Menu menu, Restaurant restaurant) {
        menu.setRestaurant(restaurant);
        return menuRepository.save(menu);
    }

    @Override
    public Menu updateMenuActiveStatus(Boolean status,Menu menu) {
        menu.setIsActive(status);
        return menuRepository.save(menu);
    }

    @Override
    public Menu getMenuById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(
                ()-> new ResourceNotFoundException("Menu not found by id:"+menuId)
        );
    }

    @Override
    public List<Menu> getMenusByRestaurant(Restaurant restaurant) {
        return menuRepository.findByRestaurant(restaurant);
    }

    @Override
    public Menu updateMenuOfRestaurant(Menu menu, Long menuId) {
        menu.setId(menuId);
        return menuRepository.save(menu);
    }
}
