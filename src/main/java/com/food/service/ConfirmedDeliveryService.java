package com.food.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.dto.ConfirmedDeliveryDto;
import com.food.enums.ConfirmedDeliveryStatus;
import com.food.model.ConfirmedDelivery;
import com.food.model.DeliveryPartner;
import com.food.model.DeliveryRequest;

public interface ConfirmedDeliveryService {
    ConfirmedDeliveryDto createConfirmedDelivery(DeliveryRequest deliveryRequest, DeliveryPartner deliveryPartner);
    ConfirmedDelivery updateConfirmedDeliveryStatus(ConfirmedDelivery confirmedDelivery,ConfirmedDeliveryStatus confirmedDeliveryStatus);
    ConfirmedDelivery getConfirmedDeliveryById(Long confirmedDeliveryId);

    Page<ConfirmedDelivery> getConfirmedDeliveriesByDeliveryPartner(DeliveryPartner deliveryPartner, PageRequest pageRequest);
}
