package com.example.RestaurantOrderingSystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenuItemsResponse {
    private List<MenuItemDTO> menuItems;
    private String message;
}
