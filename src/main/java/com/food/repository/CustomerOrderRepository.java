package com.food.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.food.model.Customer;
import com.food.model.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder,Long> {
    Page<CustomerOrder> findByCustomer(Customer customer, PageRequest pageRequest);
}
