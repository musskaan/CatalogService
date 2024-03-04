package com.example.RestaurantOrderingSystem.Service;

import com.example.RestaurantOrderingSystem.Entity.MenuItem;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.ApiResponse;
import com.example.RestaurantOrderingSystem.Model.CreateMenuItemsResponse;
import com.example.RestaurantOrderingSystem.Model.ListMenuItemsResponse;
import com.example.RestaurantOrderingSystem.Model.MenuItemDTO;
import com.example.RestaurantOrderingSystem.Repository.MenuItemsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.RestaurantOrderingSystem.Constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemsServiceTest {

    @Mock
    private RestaurantsService restaurantsService;

    @Mock
    private MenuItemsRepository menuItemsRepository;

    @InjectMocks
    private MenuItemsService menuItemsService;


    @Test
    public void testCreate_shouldReturnCreatedItemInResponse_success() {
        CreateMenuItemsResponse expectedResponse = new CreateMenuItemsResponse(List.of(menuItemDTO), SUCCESS_MESSAGE);
        when(restaurantsService.findById(VALID_RESTAURANT_ID)).thenReturn(restaurant);
        when(menuItemsRepository.findByRestaurantAndName(eq(restaurant), anyString())).thenReturn(Optional.empty());

        CreateMenuItemsResponse actualResponse = menuItemsService.create(VALID_RESTAURANT_ID, createMenuItemsRequest);

        List<MenuItemDTO> menuItemDTOList = actualResponse.getMenuItems();
        assertEquals(menuItemDTOList.getFirst().getItemName(), expectedResponse.getMenuItems().getFirst().getItemName());
        assertEquals(menuItemDTOList.getFirst().getPrice(), expectedResponse.getMenuItems().getFirst().getPrice());
        verify(restaurantsService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, times(1)).findByRestaurantAndName(restaurant, MENU_ITEM_NAME);
        verify(menuItemsRepository, times(1)).save(any(MenuItem.class));
        verify(menuItemsRepository, never()).findMenuItemsByRestaurant(restaurant);
    }

    @Test
    public void testCreate_shouldReturnCreatedItemInResponse_restaurantNotFoundInDatabase_throwsNoSuchElementException() {
        when(restaurantsService.findById(VALID_RESTAURANT_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> menuItemsService.create(VALID_RESTAURANT_ID, createMenuItemsRequest));

        verify(restaurantsService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, never()).findByRestaurantAndName(restaurant, MENU_ITEM_NAME);
        verify(menuItemsRepository, never()).save(any(MenuItem.class));
        verify(menuItemsRepository, never()).findMenuItemsByRestaurant(restaurant);
    }

    @Test
    public void testCreate_shouldReturnCreatedItemInResponse_unknownErrorFromDatabase_throwsRuntimeException() {
        when(restaurantsService.findById(VALID_RESTAURANT_ID)).thenThrow(DataRetrievalFailureException.class);

        assertThrows(RuntimeException.class, () -> menuItemsService.create(VALID_RESTAURANT_ID, createMenuItemsRequest));

        verify(restaurantsService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, never()).findByRestaurantAndName(restaurant, MENU_ITEM_NAME);
        verify(menuItemsRepository, never()).save(any(MenuItem.class));
        verify(menuItemsRepository, never()).findMenuItemsByRestaurant(restaurant);
    }

    @Test
    public void testFetchAll_shouldReturnListOfMenuItemsInResponse_success() {
        when(restaurantsService.findById(VALID_RESTAURANT_ID)).thenReturn(restaurant);
        when(menuItemsRepository.findMenuItemsByRestaurant(restaurant)).thenReturn(List.of(menuItem));

        ListMenuItemsResponse actualResponse = menuItemsService.fetchAll(VALID_RESTAURANT_ID);

        List<MenuItem> menuItems = actualResponse.getMenuItems();
        assertEquals(menuItems.getFirst().getName(), menuItem.getName());
        assertEquals(menuItems.getFirst().getPrice(), menuItem.getPrice());
        verify(restaurantsService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, times(1)).findMenuItemsByRestaurant(restaurant);
        verify(menuItemsRepository, never()).findByRestaurantAndName(restaurant, MENU_ITEM_NAME);
        verify(menuItemsRepository, never()).save(any(MenuItem.class));
    }

    @Test
    public void testFetchAll_shouldReturnListOfMenuItemsInResponse_restaurantNotFoundInDatabase_throwsNoSuchElementException() {
        when(restaurantsService.findById(VALID_RESTAURANT_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> menuItemsService.fetchAll(VALID_RESTAURANT_ID));

        verify(restaurantsService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, never()).findMenuItemsByRestaurant(restaurant);
        verify(menuItemsRepository, never()).findByRestaurantAndName(restaurant, MENU_ITEM_NAME);
        verify(menuItemsRepository, never()).save(any(MenuItem.class));
    }

    @Test
    public void testFetchAll_shouldReturnListOfMenuItemsInResponse_unknownErrorFromDatabase_throwsRuntimeException() {
        when(restaurantsService.findById(VALID_RESTAURANT_ID)).thenThrow(DataRetrievalFailureException.class);

        assertThrows(RuntimeException.class, () -> menuItemsService.fetchAll(VALID_RESTAURANT_ID));

        verify(restaurantsService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, never()).findMenuItemsByRestaurant(restaurant);
        verify(menuItemsRepository, never()).findByRestaurantAndName(restaurant, MENU_ITEM_NAME);
        verify(menuItemsRepository, never()).save(any(MenuItem.class));
    }

    @Test
    public void testFetchByName_shouldReturnMenuItem_menuItemNotFoundInDatabase_throwsNoSuchElementException() {
        when(restaurantsService.findById(VALID_RESTAURANT_ID)).thenReturn(restaurant);
        when(menuItemsRepository.findByRestaurantAndName(restaurant, MENU_ITEM_NAME)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> menuItemsService.fetchByName(String.valueOf(VALID_RESTAURANT_ID), MENU_ITEM_NAME));

        verify(restaurantsService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, times(1)).findByRestaurantAndName(restaurant, MENU_ITEM_NAME);
        verify(menuItemsRepository, never()).findMenuItemsByRestaurant(any(Restaurant.class));
        verify(menuItemsRepository, never()).save(any(MenuItem.class));
    }

    @Test
    public void testFetchByName_shouldReturnMenuItem_success() {
        when(restaurantsService.findById(VALID_RESTAURANT_ID)).thenReturn(restaurant);
        when(menuItemsRepository.findByRestaurantAndName(restaurant, MENU_ITEM_NAME)).thenReturn(Optional.of(menuItem));

        ApiResponse apiResponse = menuItemsService.fetchByName(VALID_RESTAURANT_ID.toString(), MENU_ITEM_NAME);

        assertNotNull(apiResponse);
        assertEquals(apiResponse.getStatus(), HttpStatus.OK);
        verify(restaurantsService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, times(1)).findByRestaurantAndName(restaurant, MENU_ITEM_NAME);
        verify(menuItemsRepository, never()).findMenuItemsByRestaurant(any(Restaurant.class));
        verify(menuItemsRepository, never()).save(any(MenuItem.class));
    }

    @Test
    public void testFetchByName_shouldReturnMenuItem_unknownErrorFromDatabase_throwsRuntimeException() {
        when(restaurantsService.findById(VALID_RESTAURANT_ID)).thenThrow(DataRetrievalFailureException.class);

        assertThrows(RuntimeException.class, () -> menuItemsService.fetchByName(String.valueOf(VALID_RESTAURANT_ID), MENU_ITEM_NAME));

        verify(restaurantsService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, never()).findMenuItemsByRestaurant(restaurant);
        verify(menuItemsRepository, never()).findByRestaurantAndName(restaurant, MENU_ITEM_NAME);
        verify(menuItemsRepository, never()).save(any(MenuItem.class));
    }
}