package com.food.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long id;

    private UserDto user;

    private PointDto deliveryAddress;

    @JsonIgnore
    private List<OrderRequestDto> orders;

    private Double rating;
}
