package com.food.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {

    private Long id;

    @JsonIgnore
    private UserDto user;

    private double balance = 0.0;

    @JsonIgnore
    private List<WalletTransactionDto> transactions;
}
