package com.example.RestaurantOrderingSystem.Entity;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.example.RestaurantOrderingSystem.Constants.Constants.MENU_ITEM_NAME;
import static com.example.RestaurantOrderingSystem.Constants.Constants.restaurant;
import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {

    @Test
    public void testCreateMenuItemWithEmptyName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new MenuItem(Strings.EMPTY, BigDecimal.TEN, restaurant));
    }

    @Test
    public void testCreateMenuItemWithNullPrice_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new MenuItem(MENU_ITEM_NAME, null, restaurant));
    }

    @Test
    public void testCreateMenuItemWithNegativePrice_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new MenuItem(MENU_ITEM_NAME, BigDecimal.valueOf(-10), restaurant));
    }

    @Test
    public void testCreateMenuItemWithNullRestaurant_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new MenuItem(MENU_ITEM_NAME, BigDecimal.TEN, null));
    }

    @Test
    public void testCreateValidMenuItem_success() {
        MenuItem menuItem = new MenuItem(MENU_ITEM_NAME, BigDecimal.TEN, restaurant);

        assertNotNull(menuItem);
        assertEquals(MENU_ITEM_NAME, menuItem.getName());
        assertEquals(BigDecimal.TEN, menuItem.getPrice());
        assertEquals(restaurant, menuItem.getRestaurant());
    }
}