package com.food.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.food.model.Wallet;
import com.food.model.WalletTransaction;
import com.food.repository.WalletTransactionRepository;
import com.food.service.WalletTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {
    private final WalletTransactionRepository walletTransactionRepository;

    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }

    @Override
    public Page<WalletTransaction> getAllWalletTransactionsByWallet(Wallet wallet, PageRequest pageRequest) {
        return walletTransactionRepository.findByWallet(wallet,pageRequest);
    }
}
