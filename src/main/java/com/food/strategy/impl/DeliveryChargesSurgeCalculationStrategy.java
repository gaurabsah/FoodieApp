package com.food.strategy.impl;

import org.springframework.stereotype.Service;

import com.food.strategy.DeliveryChargesCalculationStrategy;

@Service
public class DeliveryChargesSurgeCalculationStrategy implements DeliveryChargesCalculationStrategy {

    private static final Double SURGE_FACTOR = 0.02;
    @Override
    public double calculateDeliveryCharges(double totalAmount, double distance) {
        double deliveryCharges = 0.0;
        if (distance > FIXED_FREE_DELIVERY_DISTANCE) {
            deliveryCharges = FIXED_PER_KILOMETER_CHARGE * (distance - FIXED_FREE_DELIVERY_DISTANCE);
            if (totalAmount < MINIMUM_AMOUNT_FOR_FREE_DELIVERY) {
                deliveryCharges += FIXED_DELIVERY_CHARGE;
            }
        } else if (totalAmount <= MINIMUM_AMOUNT_FOR_FREE_DELIVERY) {
            deliveryCharges = FIXED_DELIVERY_CHARGE;
        }
        return deliveryCharges*SURGE_FACTOR;
    }
}
