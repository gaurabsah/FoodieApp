package com.food.service;

import com.food.dto.OrderRequestDto;
import com.food.enums.OrderRequestStatus;
import com.food.model.Cart;
import com.food.model.OrderRequest;

public interface OrderRequestService {
    OrderRequestDto createOrderRequest(OrderRequest orderRequest);
    OrderRequest updateOrderRequestStatus(OrderRequest orderRequest,OrderRequestStatus orderRequestStatus);
    OrderRequest getOrderRequestById(Long orderRequestId);
    OrderRequest getOrderRequestByCart(Cart cart);
}
