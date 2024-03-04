package com.example.RestaurantOrderingSystem.Model;

import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponse {
    private String id;

    private String name;

    private BigDecimal price;

    private String restaurantId;

    public MenuItemResponse(MenuItem menuItem) {
        this.id = String.valueOf(menuItem.getId());
        this.name = menuItem.getName();
        this.price = menuItem.getPrice();
        this.restaurantId = String.valueOf(menuItem.getRestaurant().getId());
    }
}
