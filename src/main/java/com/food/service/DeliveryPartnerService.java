package com.food.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.food.dto.ConfirmedDeliveryDto;
import com.food.dto.CustomerDto;
import com.food.dto.CustomerFeedbackDto;
import com.food.dto.DeliveryPartnerDto;
import com.food.dto.LocationDto;
import com.food.dto.WalletTransactionDto;
import com.food.model.DeliveryPartner;

public interface DeliveryPartnerService {

    ConfirmedDeliveryDto acceptDeliveryRequest(Long deliveryRequestId);
    ConfirmedDeliveryDto cancelConfirmedDelivery(Long confirmedDeliveryId);
    ConfirmedDeliveryDto completeConfirmedDeliveryOrder(Long confirmedDeliveryId, String otp);
    DeliveryPartnerDto getMyProfile();
    Page<ConfirmedDeliveryDto> getAllMyConfirmedDeliveries(PageRequest pageRequest);
    Page<WalletTransactionDto> getAllMyWalletTransactions(PageRequest pageRequest);
    DeliveryPartner getCurrentDeliveryPartner();
    DeliveryPartner updateAvailability(DeliveryPartner deliveryPartner,Boolean availability);

    CustomerDto giveFeedbackToCustomer(CustomerFeedbackDto customerFeedbackDto, Long confirmedDeliveryId);

    DeliveryPartner createNewDeliveryPartner(DeliveryPartner createDeliveryPartner);

    DeliveryPartnerDto updateMyLocation(LocationDto locationDto);
}
