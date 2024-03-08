package com.example.RestaurantOrderingSystem.Service;

import com.example.RestaurantOrderingSystem.Builder.Builder;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.CreateRestaurantResponse;
import com.example.RestaurantOrderingSystem.Model.ListRestaurantsResponse;
import com.example.RestaurantOrderingSystem.Repository.RestaurantsRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantsService {

    private final RestaurantsRepository restaurantsRepository;

    public ListRestaurantsResponse fetchAll() {
        try {
            List<Restaurant> restaurants = restaurantsRepository.findAll();
            return Builder.buildListRestaurantsResponse(restaurants);
        } catch (Exception e) {
            throw new RuntimeException("Error querying restaurants from database", e);
        }
    }

    public CreateRestaurantResponse create(Restaurant restaurant) {
        try {
            Restaurant createdRestaurant = restaurantsRepository.save(restaurant);
            return Builder.buildCreateRestaurantResponse(createdRestaurant);
        } catch (Exception e) {
            throw new RuntimeException("Error saving restaurant to database", e);
        }
    }

    public Restaurant findById(Long id) {
        Optional<Restaurant> optionalRestaurant = restaurantsRepository.findById(id);
        if (optionalRestaurant.isEmpty()) {
            throw new NoSuchElementException("Restaurant not found with id: " + id);
        }

        return optionalRestaurant.get();
    }
}
