package com.food.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import com.food.enums.DeliveryRequestStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryRequest {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    private CustomerOrder customerOrder;
    
    private Double grandTotal;
    
    private Double distance;
    
    private Integer estimatedPreparationTime;
    
    @Enumerated(EnumType.STRING)
    private DeliveryRequestStatus deliveryRequestStatus;
    
    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point pickUpAddress;
    
    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point dropOffAddress;
    
    @CreationTimestamp
    private LocalDateTime deliveryRequestedTime;
    
}