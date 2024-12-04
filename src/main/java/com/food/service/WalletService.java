package com.food.service;

import com.food.enums.TransactionMethod;
import com.food.model.CustomerOrder;
import com.food.model.User;
import com.food.model.Wallet;

public interface WalletService {
    Wallet addMoneyToWallet(User user, Double amount, String transactionId, CustomerOrder customerOrder, TransactionMethod transactionMethod);
    Wallet deductMoneyFromWallet(User user,Double amount,String transactionId,CustomerOrder customerOrder,TransactionMethod transactionMethod);
    void withDrawAllMyMoneyFromWallet();
    Wallet findWalletById(Long walletId);
    Wallet createNewWallet(User user);
    Wallet findByUser(User user);
}
