package com.example.RestaurantOrderingSystem.Entity;

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

    private String name;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public MenuItem(String name, BigDecimal price) {
        validate(name, price);
        this.name = name;
        this.price = price;
    }

    private void validate(String name, BigDecimal price) {
        if(name.isEmpty() || price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid arguments for MenuItem: " + name + " price: " + price);
        }
    }
}
