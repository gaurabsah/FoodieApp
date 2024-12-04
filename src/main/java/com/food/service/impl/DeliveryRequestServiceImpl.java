package com.food.service.impl;

import org.springframework.stereotype.Service;

import com.food.enums.DeliveryRequestStatus;
import com.food.exception.ResourceNotFoundException;
import com.food.model.CustomerOrder;
import com.food.model.DeliveryRequest;
import com.food.repository.DeliveryRequestRepository;
import com.food.service.DeliveryRequestService;
import com.food.service.DistanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryRequestServiceImpl implements DeliveryRequestService {
    private final DeliveryRequestRepository deliveryRequestRepository;
    private final DistanceService distanceService;
    @Override
    public void createDeliveryRequest(CustomerOrder customerOrder) {
        //
        DeliveryRequest deliveryRequest = DeliveryRequest.builder()
                .deliveryRequestStatus(DeliveryRequestStatus.PENDING)
                .customerOrder(customerOrder)
                .distance(distanceService.calculateDistance(customerOrder.getCustomer().getDeliveryAddress(),customerOrder.getRestaurant().getAddress()))
                .grandTotal(customerOrder.getGrandTotal())
                .pickUpAddress(customerOrder.getRestaurant().getAddress())
                .dropOffAddress(customerOrder.getCustomer().getDeliveryAddress())
                .estimatedPreparationTime(customerOrder.getEstimatedPreparationTime())
                .build();
        //
        deliveryRequestRepository.save(deliveryRequest);
        //
        //TODO-Notify nearby DeliverPersons about the delivery request
    }

    @Override
    public DeliveryRequest updateDeliveryRequestStatus(DeliveryRequest deliveryRequest, DeliveryRequestStatus deliveryRequestStatus) {
        deliveryRequest.setDeliveryRequestStatus(deliveryRequestStatus);
        return deliveryRequestRepository.save(deliveryRequest);
    }

    @Override
    public DeliveryRequest getDeliveryRequestById(Long deliveryRequestId) {
        return deliveryRequestRepository.findById(deliveryRequestId).orElseThrow(
                ()->new ResourceNotFoundException("Delivery request not found by id:"+deliveryRequestId)
        );
    }
}
