package com.food.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import com.food.enums.ConfirmedDeliveryStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfirmedDelivery {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryRequest deliveryRequest;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_person_id")
    private DeliveryPartner deliveryPartner;
    
    private String pickUpOtp;
    
    private Double distance;
    
    private Double grandTotal;
    
    private Integer estimatedTime;
    
    private Point pickUpAddress;
    
    private Point dropOffAddress;
    
    @CreationTimestamp
    private LocalDateTime deliveryAcceptedTime;
    
    private LocalDateTime deliveryCompleteTime;
    
    private ConfirmedDeliveryStatus confirmedDeliveryStatus;
    
}