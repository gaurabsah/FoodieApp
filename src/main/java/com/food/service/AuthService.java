package com.food.service;

import com.food.dto.DeliveryPartnerDto;
import com.food.dto.OnBoardDeliveryPartnerDto;
import com.food.dto.OnBoardRestaurantPartnerDto;
import com.food.dto.RestaurantPartnerDto;
import com.food.dto.SignupDto;
import com.food.dto.UserDto;

public interface AuthService {

    String[] login(String email,String password);

    UserDto signup(SignupDto signupDto);

    DeliveryPartnerDto onBoardDeliveryPartner(Long userId, OnBoardDeliveryPartnerDto onBoardDeliveryPartnerDto);

    RestaurantPartnerDto onBoardRestaurantPartner(Long userId,OnBoardRestaurantPartnerDto onBoardRestaurantPartnerDto);

    String refreshToken(String refreshToken);
}
