package com.food.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantPartnerDto {

    private Long id;

    private Long aadharNo;

    private UserDto user;
    @JsonIgnore
    private RestaurantDto restaurant;

}
