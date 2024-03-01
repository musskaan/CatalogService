package com.example.RestaurantOrderingSystem.Constants;

import com.example.RestaurantOrderingSystem.Entity.Address;
import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.ListRestaurantsResponse;

import java.math.BigDecimal;
import java.util.List;

public class Constants {

    public static final String ITEM_NAME = "pizza";

    public static final String RESTAURANT_NAME = "restaurant";

    public static final String SUCCESS_MESSAGE = "success";

    public static final MenuItem menuItem = new MenuItem(ITEM_NAME, BigDecimal.TEN);

    public static final Address address = new Address("123 Main Street", "Indore", "MP", "452001");

    public static final Restaurant restaurant = new Restaurant(RESTAURANT_NAME, List.of(menuItem), address);

    public static final ListRestaurantsResponse listRestaurantsResponse = new ListRestaurantsResponse(List.of(restaurant), SUCCESS_MESSAGE);
}
