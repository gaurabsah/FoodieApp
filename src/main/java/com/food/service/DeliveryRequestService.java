package com.food.service;

import com.food.enums.DeliveryRequestStatus;
import com.food.model.CustomerOrder;
import com.food.model.DeliveryRequest;

public interface DeliveryRequestService {
    void createDeliveryRequest(CustomerOrder customerOrder);
    DeliveryRequest updateDeliveryRequestStatus(DeliveryRequest deliveryRequest,DeliveryRequestStatus deliveryRequestStatus);
    DeliveryRequest getDeliveryRequestById(Long deliveryRequestId);
}
