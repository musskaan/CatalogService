package com.example.RestaurantOrderingSystem.Model;

import com.example.RestaurantOrderingSystem.Entity.Restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListRestaurantsResponse {
    private List<Restaurant> restaurants;
    private String message;
}
