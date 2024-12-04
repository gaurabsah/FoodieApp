package com.food.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.food.enums.CustomerOrderStatus;
import com.food.enums.PaymentMethod;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrder {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
    
    private Double totalAmount;
    
    private Double deliveryCharges;
    
    private Double platformFee;
    
    private Double grandTotal;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "restaurant_id")
    private Restaurant restaurant;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "customer_id")
    private Customer customer;
    
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    
    private Integer estimatedPreparationTime;

    @Enumerated(EnumType.STRING)
    private CustomerOrderStatus customerOrderStatus;
    
    private String otp;

    @CreationTimestamp
    private LocalDateTime orderAcceptedTime;
}