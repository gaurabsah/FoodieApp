package com.food.strategy.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.food.model.DeliveryPartner;
import com.food.model.DeliveryRequest;
import com.food.repository.DeliveryPartnerRepository;
import com.food.strategy.DeliveryPartnerMatchingStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryPartnerMatchingHighestRatedDeliveryPartnerStrategy implements DeliveryPartnerMatchingStrategy {
    private final DeliveryPartnerRepository deliveryPartnerRepository;
    @Override
    public List<DeliveryPartner> findMatchingDeliveryPartner(DeliveryRequest deliveryRequest) {
        return deliveryPartnerRepository.findTenNearbyTopRatedDeliveryPartner(deliveryRequest.getPickUpAddress());
    }
}
