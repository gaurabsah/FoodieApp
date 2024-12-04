package com.food.service.impl;

import static com.food.utils.Constants.PLATFORM_FEE;

import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.food.dto.OrderRequestDto;
import com.food.enums.OrderRequestStatus;
import com.food.exception.ResourceNotFoundException;
import com.food.model.Cart;
import com.food.model.OrderRequest;
import com.food.repository.OrderRequestRepository;
import com.food.service.DistanceService;
import com.food.service.OrderRequestService;
import com.food.strategy.CustomerOrderStrategyManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderRequestServiceImpl implements OrderRequestService {

    private final OrderRequestRepository orderRequestRepository;
    private final CustomerOrderStrategyManager customerOrderStrategyManager;
    private final DistanceService distanceService;
    private final ModelMapper modelMapper;

    @Override
    public OrderRequestDto createOrderRequest(OrderRequest orderRequest) {

        double totalAmount = orderRequest.getCart().getTotalAmount();
        Point source = orderRequest.getCustomer().getDeliveryAddress();
        Point destination = orderRequest.getRestaurant().getAddress();
        double distance = distanceService.calculateDistance(source, destination);
        double deliveryCharges = customerOrderStrategyManager.deliveryChargesCalculationStrategy().calculateDeliveryCharges(totalAmount,distance);
        //Create new order request with status pending and make status accepted after Restaurant owner accepts the order
        orderRequest.setOrderRequestStatus(OrderRequestStatus.PENDING);
        //Calculate the delivery charges and set, also set the Platform fees
        orderRequest.setDeliveryCharges(deliveryCharges);
        orderRequest.setPlatformFee(PLATFORM_FEE);
        //Set Grand total
        orderRequest.setGrandTotal(totalAmount+deliveryCharges+PLATFORM_FEE);
        //Save Order request
        OrderRequest savedOrderRequest = orderRequestRepository.save(orderRequest);
        //TODO:-Notify Restaurant owner about order
        return modelMapper.map(savedOrderRequest,OrderRequestDto.class);
    }

    @Override
    public OrderRequest updateOrderRequestStatus(OrderRequest orderRequest, OrderRequestStatus orderRequestStatus) {
        orderRequest.setOrderRequestStatus(orderRequestStatus);
        return orderRequestRepository.save(orderRequest);
    }

    @Override
    public OrderRequest getOrderRequestById(Long orderRequestId) {
        return orderRequestRepository.findById(orderRequestId).orElseThrow(
                ()-> new ResourceNotFoundException("Order request not found by id:"+orderRequestId)
        );
    }

    @Override
    public OrderRequest getOrderRequestByCart(Cart cart) {
        return orderRequestRepository.findByCart(cart).orElseThrow(
                ()->new ResourceNotFoundException("Order request not found for cart with id:"+cart.getId())
        );
    }
}
