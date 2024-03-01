package com.example.RestaurantOrderingSystem.Repository;

import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemsRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findMenuItemByRestaurantAndName(Restaurant restaurant, String menuItemName);

    List<MenuItem> findMenuItemsByRestaurant(Restaurant restaurant);
}
