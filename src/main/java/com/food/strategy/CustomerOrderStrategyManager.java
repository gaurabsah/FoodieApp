package com.food.strategy;


import java.time.LocalTime;

import org.springframework.stereotype.Component;

import com.food.strategy.impl.DeliveryChargesDefaultCalculationStrategy;
import com.food.strategy.impl.DeliveryChargesSurgeCalculationStrategy;
import com.food.strategy.impl.DeliveryPartnerMatchingHighestRatedDeliveryPartnerStrategy;
import com.food.strategy.impl.DeliveryPartnerMatchingNearestDeliveryPartnerStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerOrderStrategyManager {
    private final DeliveryPartnerMatchingHighestRatedDeliveryPartnerStrategy highestRatedDeliveryPartnerStrategy;
    private final DeliveryPartnerMatchingNearestDeliveryPartnerStrategy nearestDeliveryPartnerStrategy;
    private final DeliveryChargesDefaultCalculationStrategy deliveryChargesDefaultCalculationStrategy;
    private final DeliveryChargesSurgeCalculationStrategy deliveryChargesSurgeCalculationStrategy;

    public DeliveryPartnerMatchingStrategy deliveryPartnerMatchingStrategy(Double customerRating){
        if(customerRating >= 4.8) {
            return highestRatedDeliveryPartnerStrategy;
        } else {
            return nearestDeliveryPartnerStrategy;
        }
    }

    public DeliveryChargesCalculationStrategy deliveryChargesCalculationStrategy(){
        LocalTime surgeStartTime = LocalTime.of(18,0);
        LocalTime surgeEndTime = LocalTime.of(23,0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);
        if (isSurgeTime){
            return deliveryChargesSurgeCalculationStrategy;
        }else{
            return deliveryChargesDefaultCalculationStrategy;
        }
    }
}
