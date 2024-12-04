package com.food.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.model.Customer;
import com.food.model.CustomerOrder;
import com.food.model.DeliveryPartner;
import com.food.model.Feedback;
import com.food.model.Restaurant;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
    Optional<Feedback> findByCustomerOrder(CustomerOrder customerOrder);

    List<Feedback> findByDeliveryPartner(DeliveryPartner deliveryPartner);

    List<Feedback> findByRestaurant(Restaurant restaurant);

    List<Feedback> findByCustomer(Customer customer);
}
