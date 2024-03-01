package com.example.RestaurantOrderingSystem.Model;


import com.example.RestaurantOrderingSystem.Entity.MenuItem;
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
    List<MenuItem> menuItems;
}
