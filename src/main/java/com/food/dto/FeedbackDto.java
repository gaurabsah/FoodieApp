package com.food.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.model.Customer;
import com.food.model.CustomerOrder;
import com.food.model.DeliveryPartner;
import com.food.model.MenuItemFeedback;
import com.food.model.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDto {

    private Long id;
    private Integer deliveryRating;
    private String deliveryReview;
    private Integer restaurantRating;
    private String restaurantReview;
    private String customerReview;
    private Integer customerRating;

    @JsonIgnore
    private CustomerOrder customerOrder;

    @JsonIgnore
    private Customer customer;

    @JsonIgnore
    private Restaurant restaurant;

    @JsonIgnore
    private DeliveryPartner deliveryPartner;

    @JsonIgnore
    private List<MenuItemFeedback> menuItemsFeedback;
}
