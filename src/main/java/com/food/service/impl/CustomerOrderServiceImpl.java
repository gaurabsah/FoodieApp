package com.food.service.impl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.food.enums.CustomerOrderStatus;
import com.food.exception.ResourceNotFoundException;
import com.food.model.Customer;
import com.food.model.CustomerOrder;
import com.food.model.OrderRequest;
import com.food.repository.CustomerOrderRepository;
import com.food.service.CustomerOrderService;
import com.food.utils.OtpGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl implements CustomerOrderService {
    private final CustomerOrderRepository customerOrderRepository;
    private final ModelMapper modelMapper;
    @Override
    public CustomerOrder createCustomerOrder(OrderRequest orderRequest, Integer estimatedPreparationTime) {
        CustomerOrder customerOrder = CustomerOrder.builder()
                .customerOrderStatus(CustomerOrderStatus.PREPARING)
                .estimatedPreparationTime(estimatedPreparationTime)
                .orderAcceptedTime(LocalDateTime.now())
                .otp(OtpGenerator.generateRandomOtp())
                .totalAmount(orderRequest.getCart().getTotalAmount())
                .build();
        modelMapper.map(orderRequest,customerOrder);
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public CustomerOrder updateCustomerOrderStatus(CustomerOrder customerOrder, CustomerOrderStatus customerOrderStatus) {
        customerOrder.setCustomerOrderStatus(customerOrderStatus);
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public CustomerOrder getCustomerOrderById(Long customerOrderId) {
        return customerOrderRepository.findById(customerOrderId).orElseThrow(
                ()->new ResourceNotFoundException("Customer Order not found by id:"+customerOrderId)
        );
    }

    @Override
    public Page<CustomerOrder> getAllCustomerOrders(Customer customer, PageRequest pageRequest) {
        return customerOrderRepository.findByCustomer(customer,pageRequest);
    }
}
