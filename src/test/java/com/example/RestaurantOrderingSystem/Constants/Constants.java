package com.example.RestaurantOrderingSystem.Constants;

import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.*;

import java.math.BigDecimal;
import java.util.List;

public class Constants {

    public static final String MENU_ITEM_NAME = "pizza";

    public static final String RESTAURANT_NAME = "restaurant";

    public static final String SUCCESS_MESSAGE = "success";

    public static final String VALID_STREET = "street";

    public static final String VALID_CITY = "city";

    public static final String VALID_STATE = "state";

    public static final String VALID_ZIP_CDE = "zip";

    public static final String OK = "OK";

    public static final Long VALID_RESTAURANT_ID = 1L;

    public static final Long INVALID_RESTAURANT_ID = 2L;

    public static final Address address = new Address(VALID_STREET, VALID_CITY, VALID_STATE, VALID_ZIP_CDE);

    public static final Restaurant restaurant = new Restaurant(RESTAURANT_NAME, address);

    public static final MenuItem menuItem = new MenuItem(MENU_ITEM_NAME, BigDecimal.TEN, restaurant);

    public static final ListRestaurantsResponse listRestaurantsResponse = new ListRestaurantsResponse(List.of(restaurant), SUCCESS_MESSAGE);

    public static final CreateRestaurantResponse createRestaurantResponse = new CreateRestaurantResponse(restaurant, SUCCESS_MESSAGE);

    public static final MenuItemDTO menuItemDTO = new MenuItemDTO(MENU_ITEM_NAME, BigDecimal.TEN);

    public static final CreateMenuItemsRequest createMenuItemsRequest = new CreateMenuItemsRequest(List.of(menuItemDTO));

    public static final CreateMenuItemsResponse createMenuItemsResponse = new CreateMenuItemsResponse(List.of(menuItemDTO), SUCCESS_MESSAGE);

    public static final ListMenuItemsResponse listMenuItemsResponse = new ListMenuItemsResponse(List.of(menuItem), SUCCESS_MESSAGE);
}
