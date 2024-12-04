package com.food.dto;

import java.time.LocalDateTime;

import com.food.enums.DeliveryRequestStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestDto {

    private Long id;

    private CustomerOrderDto customerOrder;

    private Double distance;

    private Double grandTotal;

    private Integer estimatedPreparationTime;

    private DeliveryRequestStatus deliveryRequestStatus;

    private PointDto pickUpAddress;

    private PointDto dropOffAddress;

    private LocalDateTime deliveryRequestedTime;
}
