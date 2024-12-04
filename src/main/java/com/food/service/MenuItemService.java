package com.food.service;

import com.food.model.Menu;
import com.food.model.MenuItem;

public interface MenuItemService {
    MenuItem createMenuItemForMenu(MenuItem menuItem, Menu menu);
    MenuItem updateMenuItemForMenu(MenuItem menuItem, Menu menu);
    MenuItem updateMenuItemAvailabilityStatus(Boolean availableStatus,MenuItem menuItem);
    MenuItem getMenuItemById(Long menuItemId);
}
