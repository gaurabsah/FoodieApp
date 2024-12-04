package com.food.model;

import java.util.List;

import org.locationtech.jts.geom.Point;

import com.food.enums.RestaurantType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_partner_id")
	private RestaurantPartner restaurantPartner;

	private String name;

	private String description;

	@Column(columnDefinition = "Geometry(Point,4326)")
	private Point address;

	@Enumerated(EnumType.STRING)
	private RestaurantType restaurantType;

	private Double rating;

	@OneToMany(mappedBy = "restaurant")
	private List<Menu> menus;

	private Boolean isOpen;

}