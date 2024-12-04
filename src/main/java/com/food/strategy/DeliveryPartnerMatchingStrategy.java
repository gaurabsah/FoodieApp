package com.food.strategy;

import java.util.List;

import com.food.model.DeliveryPartner;
import com.food.model.DeliveryRequest;

public interface DeliveryPartnerMatchingStrategy {
    List<DeliveryPartner> findMatchingDeliveryPartner(DeliveryRequest deliveryRequest);
}
