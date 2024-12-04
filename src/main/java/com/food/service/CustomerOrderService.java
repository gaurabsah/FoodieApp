package com.food.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.enums.CustomerOrderStatus;
import com.food.model.Customer;
import com.food.model.CustomerOrder;
import com.food.model.OrderRequest;

public interface CustomerOrderService {
    CustomerOrder createCustomerOrder(OrderRequest orderRequest,Integer estimatedPreparationTime);
    CustomerOrder updateCustomerOrderStatus(CustomerOrder customerOrder, CustomerOrderStatus customerOrderStatus);
    CustomerOrder getCustomerOrderById(Long customerOrderId);

    Page<CustomerOrder> getAllCustomerOrders(Customer customer, PageRequest pageRequest);
}
