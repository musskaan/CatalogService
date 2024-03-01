package com.example.RestaurantOrderingSystem.Repository;

import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
