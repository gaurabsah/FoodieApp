package com.food.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.enums.CustomerOrderStatus;
import com.food.enums.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderDto {

    private Long id;
    private CustomerOrderStatus customerOrderStatus;
    //
    @JsonIgnore
    private CartDto cart;
    private Double totalAmount;
    private Double deliveryCharges;
    private Double platformFee;
    private Double grandTotal;
    @JsonIgnore
    private RestaurantDto restaurant;
    private CustomerDto customer;
    private PaymentMethod paymentMethod;
    private Integer estimatedPreparationTime;
    //
    private String otp;
    //
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderAcceptedTime;
}
