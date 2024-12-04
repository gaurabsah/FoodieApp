package com.food.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.dto.AcceptOrderDto;
import com.food.dto.ConfirmedDeliveryDto;
import com.food.dto.CustomerOrderDto;
import com.food.dto.MenuDto;
import com.food.dto.MenuItemDto;
import com.food.dto.MenuItemStatusDto;
import com.food.dto.MenuStatusDto;
import com.food.dto.OrderRequestDto;
import com.food.dto.RestaurantDto;
import com.food.dto.RestaurantStatusDto;
import com.food.dto.StartDeliveryDto;
import com.food.dto.WalletTransactionDto;
import com.food.service.RestaurantPartnerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurantPartner")
@Secured("ROLE_RESTAURANT_PARTNER")
public class RestaurantPartnerController {

    private final RestaurantPartnerService restaurantPartnerService;

    //Order
    @PostMapping("/acceptOrderRequest/{orderRequestId}")
    public CustomerOrderDto acceptOrderRequest(@PathVariable Long orderRequestId,@RequestBody AcceptOrderDto acceptOrderDto){
        return restaurantPartnerService.acceptOrderRequest(orderRequestId,acceptOrderDto.getEstimatedPreparationTime());
    }

    @PostMapping("/startDelivery/{confirmedDeliveryId}")
    public ResponseEntity<ConfirmedDeliveryDto> startDelivery(@PathVariable Long confirmedDeliveryId,@RequestBody StartDeliveryDto startDeliveryDto){
        return ResponseEntity.ok(restaurantPartnerService.startDelivery(confirmedDeliveryId,startDeliveryDto.getPickUpOtp()));
    }

    @PostMapping("/cancelOrder/{orderRequestId}")
    public ResponseEntity<OrderRequestDto> cancelOrderRequest(@PathVariable Long orderRequestId){
        return ResponseEntity.ok(restaurantPartnerService.cancelOrderRequest(orderRequestId));
    }

    //Restaurant
    @PostMapping("/createRestaurant")
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody @Valid RestaurantDto restaurantDto){
        return  ResponseEntity.ok(restaurantPartnerService.createRestaurant(restaurantDto));
    }
    @PutMapping("/updateRestaurantDetails/{restaurantId}")
    public ResponseEntity<RestaurantDto> updateRestaurantDetails(@RequestBody @Valid RestaurantDto restaurantDto, @PathVariable Long restaurantId){
        return ResponseEntity.ok(restaurantPartnerService.updateRestaurantDetails(restaurantDto,restaurantId));
    }
    @PutMapping("/updateRestaurantStatus/{restaurantId}")
    public ResponseEntity<RestaurantDto> updateRestaurantStatus(@RequestBody @Valid RestaurantStatusDto restaurantStatusDto,@PathVariable Long restaurantId){
        return ResponseEntity.ok(restaurantPartnerService.updateRestaurantStatus(restaurantStatusDto,restaurantId));
    }
    //Menu
    @PostMapping("/createMenuForRestaurant/{restaurantId}")
    public ResponseEntity<MenuDto> createMenuForRestaurant(@RequestBody @Valid MenuDto menuDto,@PathVariable Long restaurantId){
        return ResponseEntity.ok(restaurantPartnerService.createMenuForRestaurant(menuDto,restaurantId));
    }
    @PutMapping("/updateMenuOfRestaurant/{menuId}")
    public ResponseEntity<MenuDto> updateMenuOfRestaurant(@RequestBody @Valid MenuDto menuDto,@PathVariable Long menuId){
        return ResponseEntity.ok(restaurantPartnerService.updateMenuOfRestaurant(menuDto,menuId));
    }
    @PutMapping("/updateMenuStatus/{menuId}")
    public ResponseEntity<MenuDto> updateMenuStatus(@RequestBody @Valid MenuStatusDto menuStatusDto,@PathVariable Long menuId){
        return ResponseEntity.ok(restaurantPartnerService.updateMenuStatus(menuStatusDto,menuId));
    }
    //MenuItem
    @PostMapping("/createMenuItemForMenu/{menuId}")
    ResponseEntity<MenuItemDto> createMenuItemForMenu(@RequestBody @Valid MenuItemDto menuItemDto,@PathVariable Long menuId){
        return ResponseEntity.ok(restaurantPartnerService.createMenuItemForMenu(menuItemDto,menuId));
    }
    @PutMapping("/updateMenuItemOfMenu/{menuItemId}")
    ResponseEntity<MenuItemDto> updateMenuItemOfMenu(@RequestBody @Valid MenuItemDto menuItemDto,@PathVariable Long menuItemId){
        return ResponseEntity.ok(restaurantPartnerService.updateMenuItemOfMenu(menuItemDto,menuItemId));
    }
    @PutMapping("/updateMenuItemStatus/{menuItemId}")
    public ResponseEntity<MenuItemDto> updateMenuItemStatus(@RequestBody @Valid MenuItemStatusDto menuItemStatusDto,@PathVariable Long menuItemId){
        return ResponseEntity.ok(restaurantPartnerService.updateMenuItemStatus(menuItemStatusDto,menuItemId));
    }
    //Wallet
    @GetMapping("/walletTransactions")
    public ResponseEntity<List<WalletTransactionDto>> getWalletTransactions(@RequestParam(defaultValue = "0") Integer pageOffSet,
                                                                            @RequestParam(defaultValue = "10",required = false) Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageOffSet,pageSize, Sort.by(Sort.Direction.DESC,"timeStamp","id"));
        return ResponseEntity.ok(restaurantPartnerService.getWalletTransactions(pageRequest).getContent());
    }
}
