package com.food.strategy;

import org.springframework.stereotype.Component;

import com.food.enums.PaymentMethod;
import com.food.strategy.impl.CashPaymentStrategy;
import com.food.strategy.impl.WalletPaymentStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {
    private final CashPaymentStrategy cashPaymentStrategy;
    private final WalletPaymentStrategy walletPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod){
        return switch (paymentMethod){
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}
