package com.food.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.dto.ConfirmedDeliveryDto;
import com.food.dto.CustomerOrderDto;
import com.food.dto.MenuDto;
import com.food.dto.MenuItemDto;
import com.food.dto.MenuItemStatusDto;
import com.food.dto.MenuStatusDto;
import com.food.dto.OrderRequestDto;
import com.food.dto.RestaurantDto;
import com.food.dto.RestaurantStatusDto;
import com.food.dto.WalletTransactionDto;
import com.food.model.RestaurantPartner;

public interface RestaurantPartnerService {

    RestaurantDto createRestaurant(RestaurantDto restaurantDto);
    RestaurantDto updateRestaurantDetails(RestaurantDto restaurantDto, Long restaurantId);

    CustomerOrderDto acceptOrderRequest(Long orderRequestId, Integer estimatedPreparationTime);
    OrderRequestDto cancelOrderRequest(Long orderRequestId);

    MenuDto createMenuForRestaurant(MenuDto menuDto, Long restaurantId);

    MenuItemDto updateMenuItemOfMenu(MenuItemDto menuItemDto,Long menuItemId);

    ConfirmedDeliveryDto startDelivery(Long confirmedDeliveryId, String pickUpOtp);

    RestaurantPartner getCurrentRestaurantPartner();

    MenuDto updateMenuOfRestaurant(MenuDto menuDto, Long menuId);

    MenuItemDto createMenuItemForMenu(MenuItemDto menuItemDto, Long menuId);

    RestaurantDto updateRestaurantStatus(RestaurantStatusDto restaurantStatusDto, Long restaurantId);

    MenuDto updateMenuStatus(MenuStatusDto menuStatusDto, Long menuId);

    MenuItemDto updateMenuItemStatus(MenuItemStatusDto menuItemStatusDto, Long menuItemId);

    Page<WalletTransactionDto> getWalletTransactions(PageRequest pageRequest);

    RestaurantPartner createNewRestaurantPartner(RestaurantPartner createRestaurantPartner);
}
