package com.example.RestaurantOrderingSystem.Controller;

import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.ApiErrorResponse;
import com.example.RestaurantOrderingSystem.Model.CreateRestaurantResponse;
import com.example.RestaurantOrderingSystem.Model.ListRestaurantsResponse;
import com.example.RestaurantOrderingSystem.Service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantsService restaurantsService;

    @GetMapping
    public ResponseEntity<?> fetchAll() {
        try {
            ListRestaurantsResponse listRestaurantsResponse = restaurantsService.fetchAll();
            return ResponseEntity.status(HttpStatus.OK).body(listRestaurantsResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Restaurant restaurant) {
        try {
            CreateRestaurantResponse createRestaurantResponse = restaurantsService.create(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(createRestaurantResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}