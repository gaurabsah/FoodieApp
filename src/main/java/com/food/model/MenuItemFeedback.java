package com.food.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class MenuItemFeedback {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer menuItemRating;
    
    private String menuItemReview;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuItem menuItem;

    @ManyToOne(fetch = FetchType.LAZY)
    private Feedback feedback; // Association with the Review entity
}