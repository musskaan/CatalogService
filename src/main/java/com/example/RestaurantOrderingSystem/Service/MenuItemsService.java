package com.example.RestaurantOrderingSystem.Service;

import com.example.RestaurantOrderingSystem.Builder.Builder;
import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Exception.DuplicateMenuItemNameException;
import com.example.RestaurantOrderingSystem.Model.*;
import com.example.RestaurantOrderingSystem.Repository.MenuItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuItemsService {

    private final RestaurantsService restaurantsService;

    private final MenuItemsRepository menuItemsRepository;

    public CreateMenuItemsResponse create(Long restaurantId, CreateMenuItemsRequest createMenuItemsRequest) {
        try {
            Restaurant restaurant = restaurantsService.findById(restaurantId);
            List<MenuItemDTO> menuItemDTOList = createMenuItemsRequest.getMenuItems();

            for(MenuItemDTO menuItem : menuItemDTOList){
                String menuItemName = menuItem.getItemName();
                BigDecimal menuItemPrice = menuItem.getPrice();

                Optional<MenuItem> menuItemCheck = menuItemsRepository.findByRestaurantAndName(restaurant, menuItemName.toLowerCase());

                if (menuItemCheck.isPresent()) {
                    throw new DuplicateMenuItemNameException("Menu item with name " + menuItemName + " already exists for this restaurant.");
                }

                MenuItem createdMenuItem = new MenuItem(menuItemName.toLowerCase(), menuItemPrice, restaurant);
                menuItemsRepository.save(createdMenuItem);
            }

            return Builder.buildCreateMenuItemsResponse(createMenuItemsRequest, restaurant.getName());
        } catch (NoSuchElementException | DuplicateMenuItemNameException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error while creating menu items for restaurant: " + restaurantId + " and saving to database", e);
        }
    }

    public ListMenuItemsResponse fetchAll(Long restaurantId) {
        try {
            Restaurant restaurant = restaurantsService.findById(restaurantId);
            List<MenuItem> menuItems = menuItemsRepository.findMenuItemsByRestaurant(restaurant);
            return Builder.buildListMenuItemsResponse(menuItems, restaurant.getName());
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving menu items for restaurant: " + restaurantId, e);
        }
    }

    public ApiResponse fetchByName(String restaurantId, String menuItemName) {
        try {
            Restaurant restaurant = restaurantsService.findById(Long.valueOf(restaurantId));
            Optional<MenuItem> optionalMenuItem = menuItemsRepository.findByRestaurantAndName(restaurant, menuItemName.toLowerCase());
            if (optionalMenuItem.isEmpty()) {
                throw new NoSuchElementException("Menu item: " + menuItemName + " not found for restaurant: " + restaurant.getName());
            }

            return Builder.buildApiResponse(optionalMenuItem.get());
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving menu item: " + menuItemName + " for restaurant: " + restaurantId, e);
        }
    }
}
