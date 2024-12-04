package com.food.service.impl;

import org.springframework.stereotype.Service;

import com.food.exception.ResourceNotFoundException;
import com.food.model.Menu;
import com.food.model.MenuItem;
import com.food.repository.MenuItemRepository;
import com.food.service.MenuItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;

    @Override
    public MenuItem createMenuItemForMenu(MenuItem menuItem, Menu menu) {
        menuItem.setMenu(menu);
        menuItem.setRating(0.0);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItemForMenu(MenuItem menuItem, Menu menu) {
        menuItem.setMenu(menu);
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItemAvailabilityStatus(Boolean availableStatus, MenuItem menuItem) {
        menuItem.setIsAvailable(availableStatus);
        return menuItemRepository.save(menuItem);
    }



    @Override
    public MenuItem getMenuItemById(Long menuItemId) {
        return menuItemRepository.findById(menuItemId).orElseThrow(
                ()-> new ResourceNotFoundException("MenuItem not found by id:"+menuItemId)
        );
    }
}
