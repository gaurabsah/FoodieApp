package com.food.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.model.CustomerOrder;
import com.food.model.DeliveryRequest;

public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest,Long> {
    Optional<DeliveryRequest> findByCustomerOrder(CustomerOrder customerOrder);
}
