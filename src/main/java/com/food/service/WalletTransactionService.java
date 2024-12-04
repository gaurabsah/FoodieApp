package com.food.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.model.Wallet;
import com.food.model.WalletTransaction;

public interface WalletTransactionService {
    void createNewWalletTransaction(WalletTransaction walletTransaction);
    Page<WalletTransaction> getAllWalletTransactionsByWallet(Wallet wallet, PageRequest pageRequest);
}
