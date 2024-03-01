package com.example.RestaurantOrderingSystem.Controller;

import com.example.RestaurantOrderingSystem.Model.ListRestaurantsResponse;
import com.example.RestaurantOrderingSystem.Service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantsController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<ListRestaurantsResponse> getAllRestaurants() {
        try {
            ListRestaurantsResponse listRestaurantsResponse = restaurantService.fetchAll();
            return ResponseEntity.status(HttpStatus.OK).body(listRestaurantsResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ListRestaurantsResponse(null, e.getMessage()));
        }
    }
}
