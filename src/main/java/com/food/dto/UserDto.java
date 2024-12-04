package com.food.dto;

import java.util.Set;

import com.food.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private Long contactNumber;
    private Set<Role> roles;
}
