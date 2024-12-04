package com.food.service;

import com.food.dto.CartItemDto;
import com.food.model.Cart;
import com.food.model.CartItem;
import com.food.model.MenuItem;

public interface CartItemService {
    CartItem getCartItemById(Long cartItemId);
    CartItem createNewCartItem(MenuItem menuItem, Cart cart);
    CartItemDto incrementCartItemQuantity(Integer quantity, CartItem cartItem);
    CartItemDto decrementCartItemQuantity(Integer quantity, CartItem cartItem);
    void removeCartItemFromCart(CartItem cartItem);
}
