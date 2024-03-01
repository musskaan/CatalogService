package com.example.RestaurantOrderingSystem.Constants;

import com.example.RestaurantOrderingSystem.Entity.Address;
import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.*;

import java.math.BigDecimal;
import java.util.List;

public class Constants {

    public static final String ITEM_NAME = "pizza";

    public static final String RESTAURANT_NAME = "restaurant";

    public static final String SUCCESS_MESSAGE = "success";

    public static final Long VALID_RESTAURANT_ID = 1L;

    public static final Long INVALID_RESTAURANT_ID = 2L;

    public static final MenuItem menuItem = new MenuItem(ITEM_NAME, BigDecimal.TEN);

    public static final Address address = new Address("123 Main Street", "Indore", "MP", "452001");

    public static final Restaurant restaurant = new Restaurant(RESTAURANT_NAME, List.of(menuItem), address);

    public static final ListRestaurantsResponse listRestaurantsResponse = new ListRestaurantsResponse(List.of(restaurant), SUCCESS_MESSAGE);

    public static final CreateRestaurantResponse createRestaurantResponse = new CreateRestaurantResponse(restaurant, SUCCESS_MESSAGE);

    public static final CreateMenuItemsRequest createMenuItemsRequest = new CreateMenuItemsRequest(List.of(menuItem));

    public static final MenuItemDTO menuItemDTO = new MenuItemDTO(ITEM_NAME, BigDecimal.TEN, RESTAURANT_NAME);

    public static final CreateMenuItemsResponse createMenuItemsResponse = CreateMenuItemsResponse.builder()
            .menuItems(List.of(menuItemDTO))
            .message(SUCCESS_MESSAGE)
            .build();
}
