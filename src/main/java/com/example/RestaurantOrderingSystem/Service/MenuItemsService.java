package com.example.RestaurantOrderingSystem.Service;

import com.example.RestaurantOrderingSystem.Builder.Builder;
import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.CreateMenuItemsRequest;
import com.example.RestaurantOrderingSystem.Model.CreateMenuItemsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MenuItemsService {

    private final RestaurantService restaurantService;

    public CreateMenuItemsResponse create(Long restaurantId, CreateMenuItemsRequest createMenuItemsRequest) {
        try {
            Restaurant restaurant = restaurantService.addMenuItem(restaurantId, createMenuItemsRequest);
            return Builder.buildCreateMenuItemsResponse(restaurant, createMenuItemsRequest);
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error while creating menu items for restaurant: " + restaurantId + " and saving to database", e);
        }
    }
}
