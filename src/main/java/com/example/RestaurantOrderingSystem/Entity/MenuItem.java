package com.example.RestaurantOrderingSystem.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @JoinColumn(name="restaurant_id", nullable = false)
    @ManyToOne
    @JsonIgnore
    private Restaurant restaurant;

    public MenuItem(String name, BigDecimal price, Restaurant restaurant) {
        validate(name, price, restaurant);
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
    }

    private void validate(String name, BigDecimal price, Restaurant restaurant) {
        if(name.isEmpty() || price == null || price.compareTo(BigDecimal.ZERO) < 0 || restaurant == null) {
            throw new IllegalArgumentException("Invalid arguments for MenuItem: " + name + " price: " + price);
        }
    }
}
