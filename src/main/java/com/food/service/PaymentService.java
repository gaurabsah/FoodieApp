package com.food.service;

import com.food.model.ConfirmedDelivery;
import com.food.model.CustomerOrder;

public interface PaymentService {
    void processPayment(ConfirmedDelivery confirmedDelivery);
    void createNewPayment(CustomerOrder customerOrder);
}
