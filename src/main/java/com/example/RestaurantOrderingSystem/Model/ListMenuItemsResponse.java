package com.example.RestaurantOrderingSystem.Model;

import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListMenuItemsResponse {
    private List<MenuItem> menuItems;
    private String message;
}
