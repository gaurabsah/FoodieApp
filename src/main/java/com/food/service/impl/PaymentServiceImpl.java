package com.food.service.impl;

import org.springframework.stereotype.Service;

import com.food.enums.PaymentStatus;
import com.food.exception.ResourceNotFoundException;
import com.food.model.ConfirmedDelivery;
import com.food.model.CustomerOrder;
import com.food.model.Payment;
import com.food.repository.PaymentRepository;
import com.food.service.PaymentService;
import com.food.strategy.PaymentStrategyManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;
    @Override
    public void processPayment(ConfirmedDelivery confirmedDelivery) {
        Payment payment = paymentRepository.findByCustomerOrder(confirmedDelivery.getDeliveryRequest().getCustomerOrder()).orElseThrow(
                ()-> new ResourceNotFoundException("Payment not found for customer order with id:"+ confirmedDelivery.getId())
        );
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment,confirmedDelivery);
    }

    @Override
    public void createNewPayment(CustomerOrder customerOrder) {
        Payment payment = Payment.builder()
                .paymentMethod(customerOrder.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .customerOrder(customerOrder)
                .amount(customerOrder.getGrandTotal())
                .build();
        paymentRepository.save(payment);
    }
}
