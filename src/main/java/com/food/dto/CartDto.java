package com.food.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.enums.CartStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Long id;
    
    private String cartName;
    
    private List<CartItemDto> cartItems;
    
    private Double totalAmount;
    
    private CartStatus cartStatus;
    
    @JsonIgnore
    private CustomerDto customer;
    
    @JsonIgnore
    private RestaurantDto restaurant;
}
