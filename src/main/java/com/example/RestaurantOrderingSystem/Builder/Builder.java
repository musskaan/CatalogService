package com.example.RestaurantOrderingSystem.Builder;

import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class Builder {

    private static final String RETRIEVED_ALL_RESTAURANTS_SUCCESSFULLY = "Retrieved all restaurants successfully from database";
    private static final String SAVED_RESTAURANT_SUCCESSFULLY = "Saved restaurant successfully to database";
    private static final String SAVED_MENU_ITEM_SUCCESSFULLY = "Saved menu item successfully for the restaurant ";
    private static final String RETRIEVED_ALL_MENU_ITEMS_SUCCESSFULLY = "Retrieved all menu items for restaurant ";
    private static final String FETCHED = "Fetched";
    private static final String MENU_ITEM = "menu_item";
    private static final String RESTAURANT = "restaurant";


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

    public static ApiResponse buildApiResponse(MenuItem menuItem) {
        return ApiResponse.builder()
                .message(FETCHED)
                .status(HttpStatus.OK)
                .data(Map.of(MENU_ITEM, new MenuItemResponse(menuItem)))
                .build();
    }

    public static ApiResponse buildApiResponse(Restaurant restaurant) {
        return ApiResponse.builder()
                .message(FETCHED)
                .status(HttpStatus.OK)
                .data(Map.of(RESTAURANT, new Restaurant(restaurant.getId(), restaurant.getName(), restaurant.getAddress())))
                .build();
    }
}
