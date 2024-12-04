package com.food.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
    private Long id;
    @NotBlank(message="Name of the menu should be provided")
    private String menuName;
    private List<MenuItemDto> menuItems;
    @BooleanFlag
    @JsonProperty("isActive")
    private Boolean isActive;
    @JsonIgnore
    private RestaurantDto restaurant;
}
