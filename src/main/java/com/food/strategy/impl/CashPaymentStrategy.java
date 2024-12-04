package com.food.strategy.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.food.enums.PaymentStatus;
import com.food.enums.TransactionMethod;
import com.food.model.ConfirmedDelivery;
import com.food.model.CustomerOrder;
import com.food.model.DeliveryPartner;
import com.food.model.DeliveryRequest;
import com.food.model.Payment;
import com.food.model.RestaurantPartner;
import com.food.repository.PaymentRepository;
import com.food.service.WalletService;
import com.food.strategy.PaymentStrategy;
import static com.food.utils.Constants.COMMISSION_ON_ORDER;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {
	private final WalletService walletService;
	private final PaymentRepository paymentRepository;

	@Override
	public void processPayment(Payment payment, ConfirmedDelivery confirmedDelivery) {

		DeliveryRequest deliveryRequest = confirmedDelivery.getDeliveryRequest();
		CustomerOrder customerOrder = deliveryRequest.getCustomerOrder();

		RestaurantPartner restaurantPartner = payment.getCustomerOrder().getRestaurant().getRestaurantPartner();
		DeliveryPartner deliveryPartner = confirmedDelivery.getDeliveryPartner();
		// Deduct money from DeliveryPartner's wallet
		Double deliveryPartnerPay = calculateDeliveryPartnerPay(customerOrder.getTotalAmount(),
				customerOrder.getDeliveryCharges());
		Double amountToBeDeducted = confirmedDelivery.getGrandTotal() - deliveryPartnerPay;
		walletService.deductMoneyFromWallet(deliveryPartner.getUser(), amountToBeDeducted, null, customerOrder,
				TransactionMethod.ORDER);
		// Add money to Restaurant Owner's wallet
		Double amountToBeAdded = customerOrder.getTotalAmount()
				- (customerOrder.getTotalAmount() * COMMISSION_ON_ORDER);
		walletService.addMoneyToWallet(restaurantPartner.getUser(), amountToBeAdded, null, customerOrder,
				TransactionMethod.ORDER);
		// Update payment status
		payment.setPaymentTime(LocalDateTime.now());
		payment.setPaymentStatus(PaymentStatus.CONFIRMED);
		paymentRepository.save(payment);
	}

}
