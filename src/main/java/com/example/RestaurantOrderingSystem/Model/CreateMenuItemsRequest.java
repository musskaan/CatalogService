package com.example.RestaurantOrderingSystem.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenuItemsRequest {

    @NonNull
    private List<MenuItemDTO> menuItems;
}
