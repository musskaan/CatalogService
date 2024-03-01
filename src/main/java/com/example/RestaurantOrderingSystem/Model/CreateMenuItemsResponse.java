package com.example.RestaurantOrderingSystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMenuItemsResponse {
    List<MenuItemDTO> menuItems;
    private String message;
}
