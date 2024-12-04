package com.food.service.impl;

import static com.food.utils.Constants.AVG_SPEED;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.food.dto.ConfirmedDeliveryDto;
import com.food.enums.ConfirmedDeliveryStatus;
import com.food.exception.ResourceNotFoundException;
import com.food.model.ConfirmedDelivery;
import com.food.model.DeliveryPartner;
import com.food.model.DeliveryRequest;
import com.food.repository.ConfirmedDeliveryRepository;
import com.food.service.ConfirmedDeliveryService;
import com.food.utils.OtpGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfirmedDeliveryServiceImpl implements ConfirmedDeliveryService {
    private final ConfirmedDeliveryRepository confirmedDeliveryRepository;
    private final ModelMapper modelMapper;
    @Override
    public ConfirmedDeliveryDto createConfirmedDelivery(DeliveryRequest deliveryRequest, DeliveryPartner deliveryPartner) {

        ConfirmedDelivery confirmedDelivery = ConfirmedDelivery.builder()
                .confirmedDeliveryStatus(ConfirmedDeliveryStatus.ACCEPTED)
                .deliveryPartner(deliveryPartner)
                .deliveryRequest(deliveryRequest)
                .pickUpAddress(deliveryRequest.getPickUpAddress())
                .dropOffAddress(deliveryRequest.getDropOffAddress())
                .pickUpOtp(OtpGenerator.generateRandomOtp())
                .grandTotal(deliveryRequest.getGrandTotal())
                .distance(deliveryRequest.getDistance())
                .estimatedTime(deliveryRequest.getEstimatedPreparationTime()+estimatedDeliveryTime(deliveryRequest.getDistance())) //TODO - calculate estimated time
                .build();
        ConfirmedDelivery savedConfirmedDelivery=confirmedDeliveryRepository.save(confirmedDelivery);
        return modelMapper.map(savedConfirmedDelivery,ConfirmedDeliveryDto.class);
    }

    @Override
    public ConfirmedDelivery updateConfirmedDeliveryStatus(ConfirmedDelivery confirmedDelivery, ConfirmedDeliveryStatus confirmedDeliveryStatus) {
        confirmedDelivery.setConfirmedDeliveryStatus(confirmedDeliveryStatus);
        return confirmedDeliveryRepository.save(confirmedDelivery);
    }

    @Override
    public ConfirmedDelivery getConfirmedDeliveryById(Long confirmedDeliveryId) {
        return confirmedDeliveryRepository.findById(confirmedDeliveryId).orElseThrow(
                ()->new ResourceNotFoundException("Confirmed Delivery not found by id:"+confirmedDeliveryId)
        );
    }

    @Override
    public Page<ConfirmedDelivery> getConfirmedDeliveriesByDeliveryPartner(DeliveryPartner deliveryPartner, PageRequest pageRequest) {
        return confirmedDeliveryRepository.findByDeliveryPartner(deliveryPartner,pageRequest);
    }

    private Integer estimatedDeliveryTime(Double distance) {
        double averageSpeed = AVG_SPEED; // Average speed in km/h
        double baseTimeInMinutes = (distance / averageSpeed) * 60;
        int totalEstimatedTime = (int) Math.ceil(baseTimeInMinutes);
        return totalEstimatedTime;
    }
}
