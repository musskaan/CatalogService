package com.example.RestaurantOrderingSystem.Controller;

import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.CreateRestaurantResponse;
import com.example.RestaurantOrderingSystem.Model.ListRestaurantsResponse;
import com.example.RestaurantOrderingSystem.Service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<ListRestaurantsResponse> fetchAll() {
        try {
            ListRestaurantsResponse listRestaurantsResponse = restaurantService.fetchAll();
            return ResponseEntity.status(HttpStatus.OK).body(listRestaurantsResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ListRestaurantsResponse(null, e.getMessage()));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateRestaurantResponse> create(@RequestBody Restaurant restaurant) {
        try {
            CreateRestaurantResponse createRestaurantResponse = restaurantService.create(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(createRestaurantResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CreateRestaurantResponse(null, e.getMessage()));
        }
    }
}
