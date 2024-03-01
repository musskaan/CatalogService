package com.example.RestaurantOrderingSystem.Builder;

import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.*;

import java.util.ArrayList;
import java.util.List;

public class Builder {

    private static final String RETRIEVED_ALL_RESTAURANTS_SUCCESSFULLY = "Retrieved all restaurants successfully from database";
    private static final String SAVED_RESTAURANT_SUCCESSFULLY = "Saved restaurant successfully to database";
    private static final String SAVED_MENU_ITEM_SUCCESSFULLY = "Saved menu item successfully for the restaurant to database";


    public static ListRestaurantsResponse buildListRestaurantsResponse(List<Restaurant> restaurants) {
        return new ListRestaurantsResponse(restaurants, RETRIEVED_ALL_RESTAURANTS_SUCCESSFULLY);
    }

    public static CreateRestaurantResponse buildCreateRestaurantResponse(Restaurant restaurant) {
        return new CreateRestaurantResponse(restaurant, SAVED_RESTAURANT_SUCCESSFULLY);
    }

    public static CreateMenuItemsResponse buildCreateMenuItemsResponse(Restaurant restaurant, CreateMenuItemsRequest createMenuItemsRequest) {
        List<MenuItem> menuItems = createMenuItemsRequest.getMenuItems();
        List<MenuItemDTO> menuItemDTOList = new ArrayList<>();

        for (MenuItem menuItem : menuItems) {
            MenuItemDTO menuItemDTO = new MenuItemDTO(menuItem.getName(), menuItem.getPrice(), restaurant.getName());
            menuItemDTOList.add(menuItemDTO);
        }

        return new CreateMenuItemsResponse(menuItemDTOList, SAVED_MENU_ITEM_SUCCESSFULLY);
    }
}
