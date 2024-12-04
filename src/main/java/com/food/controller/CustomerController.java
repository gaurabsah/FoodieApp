package com.food.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.dto.CartDto;
import com.food.dto.CartItemDto;
import com.food.dto.CartItemFeedbackDto;
import com.food.dto.CartItemQuantityDto;
import com.food.dto.CustomerDto;
import com.food.dto.CustomerOrderDto;
import com.food.dto.DeliveryPartnerDto;
import com.food.dto.DeliveryPartnerFeedbackDto;
import com.food.dto.LocationDto;
import com.food.dto.MenuItemDto;
import com.food.dto.OrderRequestDto;
import com.food.dto.RemoveCartItemDto;
import com.food.dto.RestaurantDto;
import com.food.dto.RestaurantFeedbackDto;
import com.food.dto.WalletTransactionDto;
import com.food.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Secured("ROLE_CUSTOMER")
public class CustomerController {

    private final CustomerService customerService;

    //Cart
    @PostMapping("/cart/addMenuItemToCart/{menuItemId}")
    public ResponseEntity<CartItemDto> addMenuItemToCart(@PathVariable Long menuItemId){
        return ResponseEntity.ok(customerService.addMenuItemToCart(menuItemId));
    }

    @PutMapping("/cart/cartItem/incrementCartItemQuantity")
    public ResponseEntity<CartItemDto> incrementCartItemQuantity(@RequestBody CartItemQuantityDto cartItemQuantityDto){
        return ResponseEntity.ok(customerService.incrementCartItemQuantity(cartItemQuantityDto.getCartItemId(), cartItemQuantityDto.getCartId(), cartItemQuantityDto.getQuantity()));
    }

    @PutMapping("/cart/cartItem/decrementCartItemQuantity")
    public ResponseEntity<CartItemDto> decrementCartItemQuantity(@RequestBody CartItemQuantityDto cartItemQuantityDto){
        return ResponseEntity.ok(customerService.decrementCartItemQuantity(cartItemQuantityDto.getCartItemId(), cartItemQuantityDto.getCartId(), cartItemQuantityDto.getQuantity()));
    }

    @PostMapping("/cart/removeCartItem")
    public ResponseEntity<CartDto> removeCartItemFromCart(@RequestBody RemoveCartItemDto removeCartItemDto){
        return ResponseEntity.ok(customerService.removeCartItemFromCart(removeCartItemDto.getCartItemId(), removeCartItemDto.getCartId()));
    }

    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable Long cartId){
        customerService.deleteCart(cartId);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<CartDto> getCartById(@PathVariable Long cartId){
        return ResponseEntity.ok(customerService.getCartBtId(cartId));
    }
    //Order
    @PostMapping("/placeOrder/{cartId}")
    public ResponseEntity<OrderRequestDto> placeOrder(@RequestBody @Valid OrderRequestDto orderRequestDto,@PathVariable Long cartId){
        return ResponseEntity.ok(customerService.placeOrder(orderRequestDto,cartId));
    }

    @PutMapping("/cancelOrder/{orderRequestId}")
    public ResponseEntity<OrderRequestDto> cancelOrderRequest(@PathVariable Long orderRequestId){
        return ResponseEntity.ok(customerService.cancelOrderRequest(orderRequestId));
    }
    @GetMapping("/myOrders")
    public ResponseEntity<List<CustomerOrderDto>> getAllMyOrders(@RequestParam(defaultValue = "0") Integer pageOffSet,
                                                                 @RequestParam(defaultValue = "10",required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffSet,pageSize,Sort.by(Sort.Direction.DESC,"orderAcceptedTime","id"));
        return ResponseEntity.ok(customerService.getAllMyOrders(pageRequest).getContent());
    }
    //Feedback
    @PostMapping("/feedback/giveFeedbackToDeliveryPartner/{customerOrderId}")
    public ResponseEntity<DeliveryPartnerDto> giveFeedbackToDeliveryPartner(@RequestBody DeliveryPartnerFeedbackDto deliveryPartnerFeedbackDto, @PathVariable Long customerOrderId){
        return ResponseEntity.ok(customerService.giveFeedbackToDeliveryPartner(deliveryPartnerFeedbackDto,customerOrderId));
    }
    @PostMapping("/feedback/giveFeedbackToRestaurant/{customerOrderId}")
    public ResponseEntity<RestaurantDto> giveFeedbackToRestaurant(@RequestBody RestaurantFeedbackDto restaurantFeedbackDto, @PathVariable Long customerOrderId){
        return ResponseEntity.ok(customerService.giveFeedbackToRestaurant(restaurantFeedbackDto,customerOrderId));
    }
    @PostMapping("/feedback/giveFeedbackToCartItem/{customerOrderId}")
    public ResponseEntity<MenuItemDto> giveFeedbackToCartItem(@RequestBody CartItemFeedbackDto cartItemFeedbackDto, @PathVariable Long customerOrderId){
        return ResponseEntity.ok(customerService.giveFeedbackToCartItem(cartItemFeedbackDto,customerOrderId));
    }
    //Wallet Transactions
    @GetMapping("/myWalletTransactions")
    public ResponseEntity<List<WalletTransactionDto>> getMyWalletTransactions(@RequestParam(defaultValue = "0") Integer pageOffSet,
                                                                              @RequestParam(defaultValue = "10",required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffSet,pageSize,Sort.by(Sort.Direction.DESC,"timeStamp","id"));
        return ResponseEntity.ok(customerService.getAllMyWalletTransactions(pageRequest).getContent());
    }
    //Profile
    @GetMapping("/myProfile")
    public ResponseEntity<CustomerDto> getMyProfile(){
        return ResponseEntity.ok(customerService.getMyProfile());
    }

    //Restaurants
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDto>> getAllNearByRestaurants(@RequestParam(defaultValue = "0") Integer pageOffSet,
                                                                       @RequestParam(defaultValue = "10",required = false)Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffSet,pageSize);
        return ResponseEntity.ok(customerService.getAllNearByRestaurants(pageRequest).getContent());
    }

    @PutMapping("/updateMyLocation")
    public ResponseEntity<CustomerDto> updateMyLocation(@RequestBody LocationDto locationDto){
        return ResponseEntity.ok(customerService.updateMyLocation(locationDto));
    }
}
