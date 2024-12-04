package com.food.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.food.dto.MenuItemDto;
import com.food.model.Feedback;
import com.food.model.MenuItem;
import com.food.model.MenuItemFeedback;
import com.food.repository.MenuItemFeedbackRepository;
import com.food.repository.MenuItemRepository;
import com.food.service.MenuItemFeedbackService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuItemFeedbackServiceImpl implements MenuItemFeedbackService {
    private final MenuItemFeedbackRepository menuItemFeedbackRepository;
    private final ModelMapper modelMapper;
    private final MenuItemRepository menuItemRepository;

    @Override
    public List<MenuItemFeedback> getMenuItemsFeedbackByFeedback(Feedback feedback) {
        return menuItemFeedbackRepository.findByFeedback(feedback);
    }

    @Override
    public MenuItemDto giveFeedbackToCartItem(MenuItemFeedback menuItemFeedback, Integer rating, String review) {
        menuItemFeedback.setMenuItemReview(review);
        menuItemFeedback.setMenuItemRating(rating);
        MenuItemFeedback savedMenuItemFeedback = menuItemFeedbackRepository.save(menuItemFeedback);
        MenuItem menuItem = savedMenuItemFeedback.getMenuItem();
        //Update MenuItemRating
        Double newRating =menuItemFeedbackRepository.findByMenuItem(menuItem).stream()
                .mapToDouble(MenuItemFeedback::getMenuItemRating)
                .average().orElse(0.0);

        menuItem.setRating(newRating);
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return modelMapper.map(savedMenuItem, MenuItemDto.class);
    }
}
