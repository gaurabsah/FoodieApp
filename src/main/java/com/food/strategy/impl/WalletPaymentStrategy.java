package com.food.strategy.impl;

import static com.food.utils.Constants.COMMISSION_ON_ORDER;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.food.enums.PaymentStatus;
import com.food.enums.TransactionMethod;
import com.food.model.ConfirmedDelivery;
import com.food.model.Customer;
import com.food.model.CustomerOrder;
import com.food.model.DeliveryPartner;
import com.food.model.DeliveryRequest;
import com.food.model.Payment;
import com.food.model.RestaurantPartner;
import com.food.repository.PaymentRepository;
import com.food.service.WalletService;
import com.food.strategy.PaymentStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {
	private final WalletService walletService;
	private final PaymentRepository paymentRepository;

	@Override
	public void processPayment(Payment payment, ConfirmedDelivery confirmedDelivery) {
		Customer customer = payment.getCustomerOrder().getCustomer();
		DeliveryRequest deliveryRequest = confirmedDelivery.getDeliveryRequest();
		CustomerOrder customerOrder = deliveryRequest.getCustomerOrder();

		RestaurantPartner restaurantPartner = payment.getCustomerOrder().getRestaurant().getRestaurantPartner();
		DeliveryPartner deliveryPartner = confirmedDelivery.getDeliveryPartner();

		// Deduct money from customer's wallet
		Double grandTotal = customerOrder.getGrandTotal();
		walletService.deductMoneyFromWallet(customer.getUser(), grandTotal, null, customerOrder,
				TransactionMethod.ORDER);
		// Add money to delivery person's wallet
		Double amountToBeAdded = calculateDeliveryPartnerPay(customerOrder.getTotalAmount(),
				customerOrder.getDeliveryCharges());
		walletService.addMoneyToWallet(deliveryPartner.getUser(), amountToBeAdded, null, customerOrder,
				TransactionMethod.ORDER);
		// Add money to Restaurant Owner's wallet
		Double totalAmount = customerOrder.getTotalAmount() - (customerOrder.getTotalAmount() * COMMISSION_ON_ORDER);
		walletService.addMoneyToWallet(restaurantPartner.getUser(), totalAmount, null, customerOrder,
				TransactionMethod.ORDER);
		// Update payment status
		payment.setPaymentStatus(PaymentStatus.CONFIRMED);
		payment.setPaymentTime(LocalDateTime.now());
		paymentRepository.save(payment);
	}
}
