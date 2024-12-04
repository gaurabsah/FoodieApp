package com.food.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.enums.OrderRequestStatus;
import com.food.validator.PaymentTypeValidation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    private Long id;
    private CartDto cart;
    private Double deliveryCharges;
    private Double platformFee;
    private Double grandTotal;
    private OrderRequestStatus orderRequestStatus;
    @JsonIgnore
    private RestaurantDto restaurant;
    @JsonIgnore
    private CustomerDto customer;
    @NotNull
    @PaymentTypeValidation
    private String paymentMethod;
}
