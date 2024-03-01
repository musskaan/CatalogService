package com.example.RestaurantOrderingSystem.Entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
@Builder
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;

    public Address(String street, String city, String state, String zipCode) {
        validate(street, city, state, zipCode);
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    private void validate(String street, String city, String state, String zipCode) {
        if(street.isEmpty() || city.isEmpty() || state.isEmpty() || zipCode.isEmpty()) {
            throw new IllegalArgumentException("Invalid address with street: " + street + " city: " + city + " state: " + state + " zipCode: " + zipCode);
        }
    }
}