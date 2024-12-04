package com.food.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.validator.RestaurantTypeValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {

    private Long id;
    @JsonIgnore
    private RestaurantPartnerDto restaurantPartner;
    @NotBlank(message="Name of the restaurant should be provided")
    private String name;
    @NotBlank(message="Description for the restaurant should be provided")
    @NotBlank(message = "Description cannot be blank")
    @Size(max = 2000, message = "description cannot exceed 2000 characters.")
    private String description;
    @NotNull
    private PointDto address;
    @RestaurantTypeValidation
    private String restaurantType;
    private List<MenuDto> menus;
    private Double rating;
    @JsonProperty("isOpen")
    private Boolean isOpen;
}
