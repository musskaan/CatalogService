package com.example.RestaurantOrderingSystem.Model;

import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantResponse {
    private Restaurant restaurant;
    private String message;
}
