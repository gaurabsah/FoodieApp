package com.food.dto;

import com.food.model.Feedback;
import com.food.model.MenuItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemFeedbackDto {

    private Long id;
    private Integer menuItemRating;
    private String menuItemReview;

    private MenuItem menuItem;

    private Feedback feedback;
}
