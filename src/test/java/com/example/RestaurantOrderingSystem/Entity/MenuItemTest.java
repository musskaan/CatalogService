package com.example.RestaurantOrderingSystem.Entity;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.example.RestaurantOrderingSystem.Constants.Constants.ITEM_NAME;
import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {

    @Test
    public void testCreateMenuItemWithEmptyName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new MenuItem(Strings.EMPTY, BigDecimal.TEN));
    }

    @Test
    public void testCreateMenuItemWithNullPrice_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new MenuItem(ITEM_NAME, null));
    }

    @Test
    public void testCreateMenuItemWithNegativePrice_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new MenuItem(ITEM_NAME, BigDecimal.valueOf(-10)));
    }

    @Test
    public void testCreateValidMenuItem_success() {
        MenuItem menuItem = new MenuItem(ITEM_NAME, BigDecimal.TEN);

        assertNotNull(menuItem);
        assertEquals(ITEM_NAME, menuItem.getName());
        assertEquals(BigDecimal.TEN, menuItem.getPrice());
    }
}