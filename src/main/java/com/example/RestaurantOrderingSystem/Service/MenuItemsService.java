package com.example.RestaurantOrderingSystem.Service;

import com.example.RestaurantOrderingSystem.Builder.Builder;
import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Exception.DuplicateMenuItemNameException;
import com.example.RestaurantOrderingSystem.Model.CreateMenuItemsRequest;
import com.example.RestaurantOrderingSystem.Model.CreateMenuItemsResponse;
import com.example.RestaurantOrderingSystem.Model.ListMenuItemsResponse;
import com.example.RestaurantOrderingSystem.Model.MenuItemDTO;
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

    private final RestaurantService restaurantService;

    private final MenuItemsRepository menuItemsRepository;

    public CreateMenuItemsResponse create(Long restaurantId, CreateMenuItemsRequest createMenuItemsRequest) {
        try {
            Restaurant restaurant = restaurantService.findById(restaurantId);
            List<MenuItemDTO> menuItemDTOList = createMenuItemsRequest.getMenuItems();

            for(MenuItemDTO menuItem : menuItemDTOList){
                String menuItemName = menuItem.getItemName();
                BigDecimal menuItemPrice = menuItem.getPrice();

                Optional<MenuItem> menuItemCheck = menuItemsRepository.findMenuItemByRestaurantAndName(restaurant, menuItemName);

                if (menuItemCheck.isPresent()) {
                    throw new DuplicateMenuItemNameException("Menu item with name " + menuItemName + " already exists for this restaurant.");
                }

                MenuItem createdMenuItem = new MenuItem(menuItemName, menuItemPrice, restaurant);
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
            Restaurant restaurant = restaurantService.findById(restaurantId);
            List<MenuItem> menuItems = menuItemsRepository.findMenuItemsByRestaurant(restaurant);
            return Builder.buildListMenuItemsResponse(menuItems, restaurant.getName());
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving menu items for restaurant: " + restaurantId, e);
        }
    }
}
