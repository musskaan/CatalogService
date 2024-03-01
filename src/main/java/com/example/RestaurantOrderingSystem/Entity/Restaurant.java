package com.example.RestaurantOrderingSystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Address address;

    public Restaurant(String name, Address address) {
        validate(name, address);
        this.name = name;
        this.address = address;
    }

    private void validate(String name, Address address) {
        if (name.isEmpty() || address == null) {
            throw new IllegalArgumentException("Invalid arguments for Restaurant: " + name + " address: " + address);
        }
    }
}
