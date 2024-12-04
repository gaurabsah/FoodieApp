package com.food.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.dto.CartDto;
import com.food.dto.CartItemDto;
import com.food.dto.CartItemFeedbackDto;
import com.food.dto.CustomerDto;
import com.food.dto.CustomerOrderDto;
import com.food.dto.DeliveryPartnerDto;
import com.food.dto.DeliveryPartnerFeedbackDto;
import com.food.dto.LocationDto;
import com.food.dto.MenuItemDto;
import com.food.dto.OrderRequestDto;
import com.food.dto.RestaurantDto;
import com.food.dto.RestaurantFeedbackDto;
import com.food.dto.WalletTransactionDto;
import com.food.model.Customer;
import com.food.model.User;

public interface CustomerService {

    CartItemDto addMenuItemToCart(Long menuItemId);
    CartItemDto incrementCartItemQuantity(Long cartItemId, Long cartId, Integer quantity);

    CartItemDto decrementCartItemQuantity(Long cartItemId, Long cartId, Integer quantity);

    CartDto removeCartItemFromCart(Long cartItemId, Long cartId);
    void deleteCart(Long cartId);

    OrderRequestDto placeOrder(OrderRequestDto orderRequestDto,Long cartId);
    OrderRequestDto cancelOrderRequest(Long orderRequestId);

    DeliveryPartnerDto giveFeedbackToDeliveryPartner(DeliveryPartnerFeedbackDto deliveryPartnerFeedbackDto, Long customerOrderId);
    RestaurantDto giveFeedbackToRestaurant(RestaurantFeedbackDto restaurantFeedbackDto, Long customerOrderId);
    MenuItemDto giveFeedbackToCartItem(CartItemFeedbackDto cartItemFeedbackDto, Long customerOrderId);

    Page<CustomerOrderDto> getAllMyOrders(PageRequest pageRequest);

    Page<WalletTransactionDto> getAllMyWalletTransactions(PageRequest pageRequest);

    CustomerDto getMyProfile();

    Customer getCurrentCustomer();

    CartDto getCartBtId(Long cartId);

    Page<RestaurantDto> getAllNearByRestaurants(PageRequest pageRequest);

    Customer createNewCustomer(User user);

    CustomerDto updateMyLocation(LocationDto locationDto);
}
