package com.example.RestaurantOrderingSystem.Builder;

import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.CreateRestaurantResponse;
import com.example.RestaurantOrderingSystem.Model.ListRestaurantsResponse;

import java.util.List;

public class Builder {

    private static final String RETRIEVED_ALL_RESTAURANTS_SUCCESSFULLY = "Retrieved all restaurants successfully from database";
    private static final String SAVED_RESTAURANT_SUCCESSFULLY = "Saved restaurant successfully to database";


    public static ListRestaurantsResponse buildListRestaurantsResponse(List<Restaurant> restaurants) {
        return new ListRestaurantsResponse(restaurants, RETRIEVED_ALL_RESTAURANTS_SUCCESSFULLY);
    }

    public static CreateRestaurantResponse buildCreateRestaurantResponse(Restaurant restaurant) {
        return new CreateRestaurantResponse(restaurant, SAVED_RESTAURANT_SUCCESSFULLY);
    }
}
