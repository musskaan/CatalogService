package com.example.RestaurantOrderingSystem.Builder;

import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.*;

import java.util.List;

public class Builder {

    private static final String RETRIEVED_ALL_RESTAURANTS_SUCCESSFULLY = "Retrieved all restaurants successfully from database";
    private static final String SAVED_RESTAURANT_SUCCESSFULLY = "Saved restaurant successfully to database";
    private static final String SAVED_MENU_ITEM_SUCCESSFULLY = "Saved menu item successfully for the restaurant ";
    private static final String RETRIEVED_ALL_MENU_ITEMS_SUCCESSFULLY = "Retrieved all menu items for restaurant ";


    public static ListRestaurantsResponse buildListRestaurantsResponse(List<Restaurant> restaurants) {
        return new ListRestaurantsResponse(restaurants, RETRIEVED_ALL_RESTAURANTS_SUCCESSFULLY);
    }

    public static CreateRestaurantResponse buildCreateRestaurantResponse(Restaurant restaurant) {
        return new CreateRestaurantResponse(restaurant, SAVED_RESTAURANT_SUCCESSFULLY);
    }

    public static CreateMenuItemsResponse buildCreateMenuItemsResponse(CreateMenuItemsRequest createMenuItemsRequest, String restaurantName) {
        return new CreateMenuItemsResponse(createMenuItemsRequest.getMenuItems(), SAVED_MENU_ITEM_SUCCESSFULLY.concat(restaurantName));
    }

    public static ListMenuItemsResponse buildListMenuItemsResponse(List<MenuItem> menuItems, String restaurantName) {
        return new ListMenuItemsResponse(menuItems, RETRIEVED_ALL_MENU_ITEMS_SUCCESSFULLY.concat(restaurantName));
    }
}
