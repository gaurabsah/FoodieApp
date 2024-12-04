package com.food.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.food.enums.ConfirmedDeliveryStatus;
import com.food.enums.CustomerOrderStatus;
import com.food.enums.OrderRequestStatus;
import com.food.exception.AccessDeniedException;
import com.food.exception.BadRequestException;
import com.food.exception.ResourceNotFoundException;
import com.food.exception.RuntimeConflictException;
import com.food.model.ConfirmedDelivery;
import com.food.model.CustomerOrder;
import com.food.model.Menu;
import com.food.model.MenuItem;
import com.food.model.OrderRequest;
import com.food.model.Restaurant;
import com.food.model.RestaurantPartner;
import com.food.model.User;
import com.food.model.Wallet;
import com.food.repository.RestaurantPartnerRepository;
import com.food.repository.UserRepository;
import com.food.service.ConfirmedDeliveryService;
import com.food.service.CustomerOrderService;
import com.food.service.DeliveryRequestService;
import com.food.service.MenuItemService;
import com.food.service.MenuService;
import com.food.service.OrderRequestService;
import com.food.service.PaymentService;
import com.food.service.RestaurantPartnerService;
import com.food.service.RestaurantService;
import com.food.service.WalletService;
import com.food.service.WalletTransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantPartnerServiceImpl implements RestaurantPartnerService {

    private final ModelMapper modelMapper;
    private final OrderRequestService orderRequestService;
    private final ConfirmedDeliveryService confirmedDeliveryService;
    private final RestaurantService restaurantService;
    private final CustomerOrderService customerOrderService;
    private final RestaurantPartnerRepository restaurantPartnerRepository;
    private final UserRepository userRepository;
    private final DeliveryRequestService deliveryRequestService;
    private final PaymentService paymentService;
    private final MenuService menuService;
    private final MenuItemService menuItemService;
    private final WalletService walletService;
    private final WalletTransactionService walletTransactionService;

    @Override
    public RestaurantDto createRestaurant(RestaurantDto restaurantDto) {
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        //Check if restaurant owner is already having restaurant associated
        if(!(restaurantPartner.getRestaurant()==null)){
            throw new RuntimeConflictException("Restaurant owner with id:"+restaurantPartner.getId()+" is already having restaurant with id:"+restaurantPartner.getRestaurant().getId());
        }
        Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);
        restaurant.setRestaurantPartner(restaurantPartner);
        restaurant.setRating(0.0);
        return modelMapper.map(restaurantService.createRestaurant(restaurant,restaurantPartner), RestaurantDto.class);
    }

    @Override
    public RestaurantDto updateRestaurantDetails(RestaurantDto restaurantDto, Long restaurantId) {
        //Check if restaurant with given id belongs to restaurant owner
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantPartner(restaurantPartner);
        if(!restaurant.getId().equals(restaurantId)){
            throw new AccessDeniedException("Restaurant with id:"+restaurantId+" does not belong to restaurant owner with id:"+restaurantPartner.getId());
        }
        //Save restaurant with updated details
        return modelMapper.map(restaurantService.updateRestaurantDetails(restaurant,restaurantId), RestaurantDto.class);
    }

    @Override
    @Transactional
    public CustomerOrderDto acceptOrderRequest(Long orderRequestId, Integer estimatedPreparationTime) {
        OrderRequest orderRequest = orderRequestService.getOrderRequestById(orderRequestId);
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        //Check if orderRequest is of the Restaurant which belongs to current Restaurant owner
        if(!orderRequest.getRestaurant().getId().equals(restaurantPartner.getRestaurant().getId())){
            throw new AccessDeniedException("This order belongs to restaurant by id:"+orderRequest.getRestaurant().getId()+" and not to restaurant by id:"+restaurantPartner.getRestaurant().getId());
        }
        //If Order request status is not PENDING then don't allow to accept it
        if(!orderRequest.getOrderRequestStatus().equals(OrderRequestStatus.PENDING)){
            throw new BadRequestException("This order cannot be accepted, its status is:"+orderRequest.getOrderRequestStatus());
        }
        //Change order request status to accepted and save
        OrderRequest updatedOrderRequest = orderRequestService.updateOrderRequestStatus(orderRequest,OrderRequestStatus.ACCEPTED);
        //Create new Confirmed Customer order
        CustomerOrder savedCustomerOrder = customerOrderService.createCustomerOrder(updatedOrderRequest,estimatedPreparationTime);
        //TODO-Send notification to user that Order is accepted and food is preparing
        //Create new payment associated with this customer order
        paymentService.createNewPayment(savedCustomerOrder);
        //Create Delivery Request as order request is accepted by Restaurant
        deliveryRequestService.createDeliveryRequest(savedCustomerOrder);
        return modelMapper.map(savedCustomerOrder, CustomerOrderDto.class);
    }

    @Override
    public OrderRequestDto cancelOrderRequest(Long orderRequestId) {
        OrderRequest orderRequest = orderRequestService.getOrderRequestById(orderRequestId);
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        //Check if this order request is for the restaurant owner's restaurant
        if(!orderRequest.getRestaurant().getId().equals(restaurantPartner.getId())){
            throw new AccessDeniedException("This order request does not belong to the restaurant with id:"+restaurantPartner.getRestaurant().getId());
        }
        //Allow to cancel order only if order request status is Pending
        if(!orderRequest.getOrderRequestStatus().equals(OrderRequestStatus.PENDING)){
            throw new BadRequestException("Order request cannot be cancelled its status is:"+orderRequest.getOrderRequestStatus().name());
        }
        //Change order request status to cancelled
        return modelMapper.map(orderRequestService.updateOrderRequestStatus(orderRequest,OrderRequestStatus.CANCELLED), OrderRequestDto.class);
    }

    @Override
    public MenuDto createMenuForRestaurant(MenuDto menuDto, Long restaurantId) {
        //Check if restaurant with given id belongs to restaurant owner
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantPartner(restaurantPartner);
        if(!restaurant.getId().equals(restaurantId)){
            throw new AccessDeniedException("Restaurant with id:"+restaurantId+" does not belong to restaurant owner with id:"+restaurantPartner.getId());
        }
        //Save menu
        Menu menu = modelMapper.map(menuDto, Menu.class);
        return modelMapper.map(menuService.createNewMenuForRestaurant(menu,restaurant), MenuDto.class);
    }

    @Override
    public MenuItemDto updateMenuItemOfMenu(MenuItemDto menuItemDto, Long menuItemId) {
        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);
        Menu menu = menuItem.getMenu();
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantPartner(restaurantPartner);
        //Check if menu belongs to restaurant owners restaurant
        if(!menu.getRestaurant().getId().equals(restaurant.getId())){
            throw new AccessDeniedException("Menu with id:"+menu.getId()+" does not belong to restaurant with id:"+restaurant.getId());
        }
        MenuItem updatedMenuItem = modelMapper.map(menuItemDto, MenuItem.class);
        updatedMenuItem.setId(menuItemId);
        return modelMapper.map(menuItemService.updateMenuItemForMenu(updatedMenuItem,menu), MenuItemDto.class);
    }

    @Override
    @Transactional
    public ConfirmedDeliveryDto startDelivery(Long confirmedDeliveryId, String pickUpOtp) {
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        ConfirmedDelivery confirmedDelivery = confirmedDeliveryService.getConfirmedDeliveryById(confirmedDeliveryId);
        //Check this confirmed delivery belongs to restaurant owner's restaurant
        if(!confirmedDelivery.getDeliveryRequest().getCustomerOrder().getRestaurant().getId().equals(restaurantPartner.getRestaurant().getId())){
            throw new AccessDeniedException("Delivery cannot be started this delivery does not belong to restaurant having id:"+restaurantPartner.getRestaurant().getId());
        }
        //Start delivery only if delivery status is ACCEPTED
        if(!confirmedDelivery.getConfirmedDeliveryStatus().equals(ConfirmedDeliveryStatus.ACCEPTED)){
            throw new BadRequestException("Cannot start delivery confirmed delivery status is:"+confirmedDelivery.getConfirmedDeliveryStatus().name());
        }
        //Check if otp is correct or not
        if(!pickUpOtp.equals(confirmedDelivery.getPickUpOtp())){
            throw new BadRequestException("OTP is invalid!");
        }
        //
        CustomerOrder customerOrder = confirmedDelivery.getDeliveryRequest().getCustomerOrder();
        //Update customer order status to Out for delivery
        customerOrder.setEstimatedPreparationTime(0);
        CustomerOrder updatedCustomerOrder = customerOrderService.updateCustomerOrderStatus(customerOrder, CustomerOrderStatus.OUT_FOR_DELIVERY);
        //Update confirmed delivery status to Out for delivery
        confirmedDelivery.getDeliveryRequest().setCustomerOrder(updatedCustomerOrder);
        ConfirmedDelivery updatedConfirmedDelivery = confirmedDeliveryService.updateConfirmedDeliveryStatus(confirmedDelivery,ConfirmedDeliveryStatus.OUT_FOR_DELIVERY);
        //TODO - Send email or notification to customer that order is out for delivery
        return modelMapper.map(updatedConfirmedDelivery, ConfirmedDeliveryDto.class);
    }

    @Override
    public MenuDto updateMenuOfRestaurant(MenuDto menuDto, Long menuId) {
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantPartner(restaurantPartner);
        Menu menu = menuService.getMenuById(menuId);
        modelMapper.map(menuDto,menu);
        menu.setRestaurant(restaurant);
        //Check if this menu belongs to restaurant owners restaurant
        if(!menu.getRestaurant().getId().equals(restaurant.getId())){
            throw new AccessDeniedException("Menu with id:"+menuId+" does not belong to restaurant with id:"+restaurant.getId());
        }
        return modelMapper.map(menuService.updateMenuOfRestaurant(menu,menuId),MenuDto.class);
    }

    @Override
    public MenuItemDto createMenuItemForMenu(MenuItemDto menuItemDto, Long menuId) {
        Menu menu = menuService.getMenuById(menuId);
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantPartner(restaurantPartner);
        //Check if this menu belongs to restaurant owners restaurant
        if(!menu.getRestaurant().getId().equals(restaurant.getId())){
            throw new AccessDeniedException("Menu with id:"+menuId+" does not belong to restaurant with id:"+restaurant.getId());
        }
        MenuItem menuItem = modelMapper.map(menuItemDto, MenuItem.class);
        return modelMapper.map(menuItemService.createMenuItemForMenu(menuItem,menu), MenuItemDto.class);
    }

    @Override
    public RestaurantDto updateRestaurantStatus(RestaurantStatusDto restaurantStatusDto, Long restaurantId) {
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantPartner(restaurantPartner);
        //Check if restaurant with given id belongs to restaurant owner
        if(!restaurant.getId().equals(restaurantId)){
            throw new AccessDeniedException("Restaurant with id:"+restaurantId+" does not belong to restaurant owner with id:"+restaurantPartner.getId());
        }
        return modelMapper.map(restaurantService.updateRestaurantStatus(restaurantStatusDto.getIsOpen(),restaurant), RestaurantDto.class);
    }

    @Override
    public MenuDto updateMenuStatus(MenuStatusDto menuStatusDto, Long menuId) {
        Menu menu = menuService.getMenuById(menuId);
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantPartner(restaurantPartner);
        //Check if menu belongs to the restaurant owners restaurant
        if(!restaurant.getId().equals(menu.getRestaurant().getId())){
            throw new AccessDeniedException("Menu with id:"+menuId+" does not belong to restaurant with id:"+restaurant.getId());
        }
        return modelMapper.map(menuService.updateMenuActiveStatus(menuStatusDto.getIsActive(),menu), MenuDto.class);
    }

    @Override
    public MenuItemDto updateMenuItemStatus(MenuItemStatusDto menuItemStatusDto, Long menuItemId) {
        MenuItem menuItem = menuItemService.getMenuItemById(menuItemId);
        Menu menu = menuItem.getMenu();
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        Restaurant restaurant = restaurantService.getRestaurantByRestaurantPartner(restaurantPartner);
        //Check if menu belongs to restaurant owner's restaurant
        if(!menu.getRestaurant().getId().equals(restaurant.getId())){
            throw new AccessDeniedException("Menu with id:"+menu.getId()+" does not belong to restaurant with id:"+restaurant.getId());
        }
        return modelMapper.map(menuItemService.updateMenuItemAvailabilityStatus(menuItemStatusDto.getIsAvailable(),menuItem), MenuItemDto.class);
    }

    @Override
    public Page<WalletTransactionDto> getWalletTransactions(PageRequest pageRequest) {
        RestaurantPartner restaurantPartner = getCurrentRestaurantPartner();
        Wallet wallet = walletService.findByUser(restaurantPartner.getUser());
        return walletTransactionService.getAllWalletTransactionsByWallet(wallet,pageRequest).map(
                walletTransaction -> modelMapper.map(walletTransaction, WalletTransactionDto.class)
        );
    }

    @Override
    public RestaurantPartner createNewRestaurantPartner(RestaurantPartner createRestaurantPartner) {
        return restaurantPartnerRepository.save(createRestaurantPartner);
    }

    @Override
    public RestaurantPartner getCurrentRestaurantPartner() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return restaurantPartnerRepository.findByUser(user).orElseThrow(
                ()->new ResourceNotFoundException("Retaurant owner not found by user id:"+user.getId())
        );
    }
}
