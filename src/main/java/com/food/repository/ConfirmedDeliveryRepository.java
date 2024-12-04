package com.food.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.model.ConfirmedDelivery;
import com.food.model.DeliveryPartner;

@Repository
public interface ConfirmedDeliveryRepository extends JpaRepository<ConfirmedDelivery,Long> {
    Page<ConfirmedDelivery> findByDeliveryPartner(DeliveryPartner deliveryPartner, PageRequest pageRequest);
}
