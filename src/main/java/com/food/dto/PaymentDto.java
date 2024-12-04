package com.food.dto;

import java.time.LocalDateTime;

import com.food.enums.PaymentMethod;
import com.food.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long id;

    private PaymentMethod paymentMethod;

    private CustomerOrderDto customerOrder;

    private PaymentStatus paymentStatus;

    private double amount;

    private LocalDateTime paymentTime;
}
