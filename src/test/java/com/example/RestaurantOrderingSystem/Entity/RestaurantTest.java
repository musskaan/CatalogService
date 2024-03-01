package com.example.RestaurantOrderingSystem.Entity;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;

import static com.example.RestaurantOrderingSystem.Constants.Constants.RESTAURANT_NAME;
import static com.example.RestaurantOrderingSystem.Constants.Constants.address;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {


    @Test
    public void testCreateRestaurantWithEmptyName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Restaurant(Strings.EMPTY, address));
    }

    @Test
    public void testCreateRestaurantWithNullAddress_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Restaurant(RESTAURANT_NAME,null));
    }

    @Test
    public void testCreateValidRestaurant_success() {
        Restaurant restaurant = new Restaurant(RESTAURANT_NAME, address);

        assertNotNull(restaurant);
        assertEquals(RESTAURANT_NAME, restaurant.getName());
        assertEquals(address, restaurant.getAddress());
    }
}

