package com.food.service;

import java.util.List;

import com.food.dto.MenuItemDto;
import com.food.model.Feedback;
import com.food.model.MenuItemFeedback;

public interface MenuItemFeedbackService {

    List<MenuItemFeedback> getMenuItemsFeedbackByFeedback(Feedback feedback);

    MenuItemDto giveFeedbackToCartItem(MenuItemFeedback menuItemFeedback, Integer rating, String review);
}
