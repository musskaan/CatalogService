package com.example.RestaurantOrderingSystem.Builder;

import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.ListRestaurantsResponse;

import java.util.List;

public class Builder {

    private static final String SUCCESS_MESSAGE = "Retrieved all restaurants successfully";

    public static ListRestaurantsResponse buildListRestaurantsResponse(List<Restaurant> restaurants) {
        return new ListRestaurantsResponse(restaurants, SUCCESS_MESSAGE);
    }
}
