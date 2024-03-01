package com.example.RestaurantOrderingSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "restaurant_id")
    private List<MenuItem> menuItems = new ArrayList<>();

    private Address address;

    public Restaurant(String name, List<MenuItem> menuItems, Address address) {
        validate(name, menuItems, address);
        this.name = name;
        this.menuItems = menuItems;
        this.address = address;
    }

    private void validate(String name, List<MenuItem> menuItems, Address address) {
        if(name.isEmpty() || menuItems == null || address == null) {
            throw new IllegalArgumentException("Invalid arguments for Restaurant: " + name + " menu items: " + menuItems + " address: " + address);
        }
    }

    public void addMenuItems(List<MenuItem> menuItems) {
        this.menuItems.addAll(menuItems);
    }
}
