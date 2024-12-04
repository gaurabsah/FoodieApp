package com.food.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.food.model.Wallet;
import com.food.model.WalletTransaction;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction,Long> {
    Page<WalletTransaction> findByWallet(Wallet wallet, PageRequest pageRequest);
}
