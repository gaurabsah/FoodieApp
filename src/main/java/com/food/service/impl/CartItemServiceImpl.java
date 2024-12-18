package com.food.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.food.dto.CartItemDto;
import com.food.exception.BadRequestException;
import com.food.exception.ResourceNotFoundException;
import com.food.model.Cart;
import com.food.model.CartItem;
import com.food.model.MenuItem;
import com.food.repository.CartItemRepository;
import com.food.service.CartItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;

    @Override
    public CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(
                ()-> new ResourceNotFoundException("Cart Item not found by id:"+cartItemId)
        );
    }

    @Override
    public CartItem createNewCartItem(MenuItem menuItem, Cart cart) {
        //Update the cart total price as we are adding new cart-item to cart, this will automatically update the cart on saving cart-item entity as we are using CASCADE MERGE in Cart-Item entity
        cart.setTotalAmount(cart.getTotalAmount()+menuItem.getPrice());
        CartItem cartItem = CartItem.builder()
                .quantity(1)
                .menuItem(menuItem)
                .cart(cart)
                .build();
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItemDto incrementCartItemQuantity(Integer quantity, CartItem cartItem) {
        if (quantity == 0 || quantity<0){
            throw new BadRequestException("Quantity has to be greater than zero");
        }
        cartItem.setQuantity(cartItem.getQuantity()+quantity);
        //Update the total price of the cart as the quantity is increased, on saving cartItem will update cart also as we have added cascade type merge in cart-item entity
        Cart cart = cartItem.getCart();
        cart.setTotalAmount(cart.getTotalAmount()+cartItem.getMenuItem().getPrice()*quantity);
        cartItem.setCart(cart);
        return modelMapper.map(cartItemRepository.save(cartItem),CartItemDto.class);
    }
    @Override
    public CartItemDto decrementCartItemQuantity(Integer quantity, CartItem cartItem) {
        if (quantity == 0){
            throw new BadRequestException("Quantity has to be greater than zero");
        }else if(cartItem.getQuantity() == 0){
            throw new BadRequestException("Quantity already set to low");
        } else if (cartItem.getQuantity()-quantity<0) {
            throw new BadRequestException("Quantity must be less than or equal to "+cartItem.getQuantity());
        }
        cartItem.setQuantity(cartItem.getQuantity()-quantity);
        //Update the total price of the cart as the quantity is decreased, on saving cartItem will update cart also as we have added cascade type merge in cart-item entity
        Cart cart = cartItem.getCart();
        cart.setTotalAmount(cart.getTotalAmount()-cartItem.getMenuItem().getPrice()*quantity);
        cartItem.setCart(cart);
        return modelMapper.map(cartItemRepository.save(cartItem),CartItemDto.class);
    }

    @Override
    public void removeCartItemFromCart(CartItem cartItem) {
        cartItemRepository.deleteById(cartItem.getId());
    }
}
