package com.food.strategy;

import com.food.model.ConfirmedDelivery;
import com.food.model.Payment;
import static com.food.strategy.DeliveryChargesCalculationStrategy.FIXED_DELIVERY_CHARGE;
import static com.food.strategy.DeliveryChargesCalculationStrategy.MINIMUM_AMOUNT_FOR_FREE_DELIVERY;

public interface PaymentStrategy {
	void processPayment(Payment payment, ConfirmedDelivery confirmedDelivery);

	default Double calculateDeliveryPartnerPay(Double totalAmount, Double deliveryCharges) {
		Double pay = FIXED_DELIVERY_CHARGE;
		if (totalAmount >= MINIMUM_AMOUNT_FOR_FREE_DELIVERY) { // It was a free delivery but based on distance
																// additional charges may have applied
			pay += deliveryCharges;
		} else { // It was not a free delivery base pay of delivery person is already included
			pay = deliveryCharges;
		}
		return pay;
	}
}
