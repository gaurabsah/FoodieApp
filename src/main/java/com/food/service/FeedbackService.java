package com.food.service;

import com.food.dto.CustomerDto;
import com.food.dto.DeliveryPartnerDto;
import com.food.dto.RestaurantDto;
import com.food.model.ConfirmedDelivery;
import com.food.model.CustomerOrder;
import com.food.model.Feedback;

public interface FeedbackService {

    void createNewFeedback(ConfirmedDelivery confirmedDelivery);
    DeliveryPartnerDto giveFeedbackToDeliveryPartner(Feedback feedback, Integer rating, String review);
    RestaurantDto giveFeedbackToRestaurant(Feedback feedback, Integer rating, String review);
    CustomerDto giveFeedbackToCustomer(Feedback feedback, Integer rating, String review);
    Feedback getFeedbackByCustomerOrder(CustomerOrder customerOrder);
}
