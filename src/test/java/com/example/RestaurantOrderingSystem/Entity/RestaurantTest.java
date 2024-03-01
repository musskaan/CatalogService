package com.example.RestaurantOrderingSystem.Entity;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.RestaurantOrderingSystem.Constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {


    @Test
    public void testCreateRestaurantWithEmptyName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Restaurant(Strings.EMPTY, List.of(menuItem), address));
    }

    @Test
    public void testCreateRestaurantWithNullMenuItems_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Restaurant(RESTAURANT_NAME, null, address));
    }

    @Test
    public void testCreateRestaurantWithNullAddress_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Restaurant(RESTAURANT_NAME, List.of(menuItem), null));
    }

    @Test
    public void testCreateValidRestaurant_success() {
        Restaurant restaurant = new Restaurant(RESTAURANT_NAME, List.of(menuItem), address);

        assertNotNull(restaurant);
        assertEquals(RESTAURANT_NAME, restaurant.getName());
        assertEquals(1, restaurant.getMenuItems().size());
        assertTrue(restaurant.getMenuItems().contains(menuItem));
        assertEquals(address, restaurant.getAddress());
    }
}

