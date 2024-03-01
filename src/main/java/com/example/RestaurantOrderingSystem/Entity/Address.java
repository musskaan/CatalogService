package com.example.RestaurantOrderingSystem.Entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;
}