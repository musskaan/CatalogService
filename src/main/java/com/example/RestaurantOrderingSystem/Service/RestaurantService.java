package com.example.RestaurantOrderingSystem.Service;

import com.example.RestaurantOrderingSystem.Builder.Builder;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.CreateMenuItemsRequest;
import com.example.RestaurantOrderingSystem.Model.CreateRestaurantResponse;
import com.example.RestaurantOrderingSystem.Model.ListRestaurantsResponse;
import com.example.RestaurantOrderingSystem.Repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public ListRestaurantsResponse fetchAll() {
        try {
            List<Restaurant> restaurants = restaurantRepository.findAll();
            return Builder.buildListRestaurantsResponse(restaurants);
        } catch (Exception e) {
            throw new RuntimeException("Error querying restaurants from database", e);
        }
    }

    public CreateRestaurantResponse create(Restaurant restaurant) {
        try {
            Restaurant createdRestaurant = restaurantRepository.save(restaurant);
            return Builder.buildCreateRestaurantResponse(createdRestaurant);
        } catch (Exception e) {
            throw new RuntimeException("Error saving restaurant to database", e);
        }
    }

    public Restaurant addMenuItem(Long restaurantId, CreateMenuItemsRequest createMenuItemsRequest) throws Exception {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);

        if (optionalRestaurant.isEmpty()) {
            throw new NoSuchElementException("Restaurant not found with id: " + restaurantId);
        }

        Restaurant restaurant = optionalRestaurant.get();
        restaurant.addMenuItems(createMenuItemsRequest.getMenuItems());
        return restaurantRepository.save(restaurant);
    }
}
