package com.example.RestaurantOrderingSystem.Repository;

import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemsRepository extends JpaRepository<MenuItem, Long> {
}
