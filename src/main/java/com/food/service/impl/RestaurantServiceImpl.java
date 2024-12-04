package com.food.service.impl;

import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.food.exception.ResourceNotFoundException;
import com.food.model.Restaurant;
import com.food.model.RestaurantPartner;
import com.food.repository.RestaurantRepository;
import com.food.service.RestaurantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    @Override
    public Restaurant getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(
                ()-> new ResourceNotFoundException("Restaurant not found by id:"+restaurantId)
        );
    }

    @Override
    public Restaurant createRestaurant(Restaurant restaurant,RestaurantPartner restaurantPartner) {
        restaurant.setRestaurantPartner(restaurantPartner);
        restaurant.setRating(0.0);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant getRestaurantByRestaurantPartner(RestaurantPartner restaurantPartner) {
        return restaurantRepository.findByRestaurantPartner(restaurantPartner).orElseThrow(
                ()->new ResourceNotFoundException("No restaurant found for restaurant owner with id:"+restaurantPartner.getId())
        );
    }

    @Override
    public Restaurant updateRestaurantDetails(Restaurant restaurant, Long restaurantId) {
        restaurant.setId(restaurantId);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurantStatus(Boolean value, Restaurant restaurant) {
        restaurant.setIsOpen(value);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Page<Restaurant> getRestaurantsNearByCustomer(Point customerLocation, PageRequest pageRequest) {
        return restaurantRepository.findNearByRestaurants(customerLocation,pageRequest);
    }
}
