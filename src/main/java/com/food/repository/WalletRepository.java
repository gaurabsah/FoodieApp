package com.food.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.model.User;
import com.food.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Optional<Wallet> findByUser(User user);
}
