package com.food.service;

import java.util.Optional;

import com.food.dto.CartDto;
import com.food.enums.CartStatus;
import com.food.model.Cart;
import com.food.model.CartItem;
import com.food.model.Customer;
import com.food.model.Restaurant;

public interface CartService {

    void deleteCart(Long cartId);
    CartDto removeCartItemFromCart(CartItem cartItem,Cart cart);
    Cart getCartById(Long cartId);
    Cart createNewCart(Restaurant restaurant,Customer customer);
    Optional<Cart> getCartByCustomerAndRestaurantAndCartStatus(Customer customer, Restaurant restaurant, CartStatus cartStatus);
    Cart updateCartStatus(Cart cart, CartStatus cartStatus);
}
