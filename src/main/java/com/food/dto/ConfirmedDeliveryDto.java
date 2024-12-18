package com.food.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.enums.ConfirmedDeliveryStatus;
import com.food.model.DeliveryRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmedDeliveryDto {
    private Long id;
    @JsonIgnore
    private DeliveryRequest deliveryRequest;
    private DeliveryPartnerDto deliveryPartner;
    private String pickUpOtp;
    private Double distance;
    private Double grandTotal;
    private Integer estimatedTime;
    private PointDto pickUpAddress;
    private PointDto dropOffAddress;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deliveryAcceptedTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deliveryCompleteTime;
    private ConfirmedDeliveryStatus confirmedDeliveryStatus;
}
