package com.food.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPartnerDto {

    private Long id;
    private Long aadharNo;
    private String vehicleId;
    private UserDto user;
    private PointDto currentLocation;
    @JsonProperty("isAvailable")
    private Boolean isAvailable;
    private Double rating;
    @JsonIgnore
    private List<ConfirmedDeliveryDto> confirmedDeliveries;
}
