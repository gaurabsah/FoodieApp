package com.food.service.impl;

import org.springframework.stereotype.Service;

import com.food.enums.TransactionMethod;
import com.food.enums.TransactionType;
import com.food.exception.ResourceNotFoundException;
import com.food.model.CustomerOrder;
import com.food.model.User;
import com.food.model.Wallet;
import com.food.model.WalletTransaction;
import com.food.repository.WalletRepository;
import com.food.service.WalletService;
import com.food.service.WalletTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletTransactionService walletTransactionService;
    private final WalletRepository walletRepository;
    @Override
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId, CustomerOrder customerOrder, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()+amount);
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .customerOrder(customerOrder)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId, CustomerOrder customerOrder, TransactionMethod transactionMethod) {
        Wallet wallet = findByUser(user);
        wallet.setBalance(wallet.getBalance()-amount);
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .customerOrder(customerOrder)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);

        return walletRepository.save(wallet);
    }

    @Override
    public void withDrawAllMyMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {
        return null;
    }

    @Override
    public Wallet createNewWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findByUser(User user) {
        return walletRepository.findByUser(user).orElseThrow(
                ()-> new ResourceNotFoundException("Wallet not found for user with id:"+user.getId())
        );
    }
}
