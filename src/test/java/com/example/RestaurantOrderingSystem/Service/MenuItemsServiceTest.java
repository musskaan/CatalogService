package com.example.RestaurantOrderingSystem.Service;

import com.example.RestaurantOrderingSystem.Entity.MenuItem;
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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.RestaurantOrderingSystem.Constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemsServiceTest {

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private MenuItemsRepository menuItemsRepository;

    @InjectMocks
    private MenuItemsService menuItemsService;


    @Test
    public void testCreate_shouldReturnCreatedItemInResponse_success() {
        CreateMenuItemsResponse expectedResponse = new CreateMenuItemsResponse(List.of(menuItemDTO), SUCCESS_MESSAGE);
        when(restaurantService.findById(VALID_RESTAURANT_ID)).thenReturn(restaurant);
        when(menuItemsRepository.findMenuItemByRestaurantAndName(eq(restaurant), anyString())).thenReturn(Optional.empty());

        CreateMenuItemsResponse actualResponse = menuItemsService.create(VALID_RESTAURANT_ID, createMenuItemsRequest);

        List<MenuItemDTO> menuItemDTOList = actualResponse.getMenuItems();
        assertEquals(menuItemDTOList.getFirst().getItemName(), expectedResponse.getMenuItems().getFirst().getItemName());
        assertEquals(menuItemDTOList.getFirst().getPrice(), expectedResponse.getMenuItems().getFirst().getPrice());
        verify(restaurantService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, times(1)).findMenuItemByRestaurantAndName(restaurant, ITEM_NAME);
        verify(menuItemsRepository, times(1)).save(any(MenuItem.class));
        verify(menuItemsRepository, never()).findMenuItemsByRestaurant(restaurant);
    }

    @Test
    public void testCreate_shouldReturnCreatedItemInResponse_restaurantNotFoundInDatabase_throwsNoSuchElementException() {
        when(restaurantService.findById(VALID_RESTAURANT_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> menuItemsService.create(VALID_RESTAURANT_ID, createMenuItemsRequest));

        verify(restaurantService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, never()).findMenuItemByRestaurantAndName(restaurant, ITEM_NAME);
        verify(menuItemsRepository, never()).save(any(MenuItem.class));
        verify(menuItemsRepository, never()).findMenuItemsByRestaurant(restaurant);
    }

    @Test
    public void testCreate_shouldReturnCreatedItemInResponse_unknownErrorFromDatabase_throwsRuntimeException() {
        when(restaurantService.findById(VALID_RESTAURANT_ID)).thenThrow(DataRetrievalFailureException.class);

        assertThrows(RuntimeException.class, () -> menuItemsService.create(VALID_RESTAURANT_ID, createMenuItemsRequest));

        verify(restaurantService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, never()).findMenuItemByRestaurantAndName(restaurant, ITEM_NAME);
        verify(menuItemsRepository, never()).save(any(MenuItem.class));
        verify(menuItemsRepository, never()).findMenuItemsByRestaurant(restaurant);
    }

    @Test
    public void testFetchAll_shouldReturnListOfMenuItemsInResponse_success() {
        when(restaurantService.findById(VALID_RESTAURANT_ID)).thenReturn(restaurant);
        when(menuItemsRepository.findMenuItemsByRestaurant(restaurant)).thenReturn(List.of(menuItem));

        ListMenuItemsResponse actualResponse = menuItemsService.fetchAll(VALID_RESTAURANT_ID);

        List<MenuItem> menuItems = actualResponse.getMenuItems();
        assertEquals(menuItems.getFirst().getName(), menuItem.getName());
        assertEquals(menuItems.getFirst().getPrice(), menuItem.getPrice());
        verify(restaurantService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, times(1)).findMenuItemsByRestaurant(restaurant);
        verify(menuItemsRepository, never()).findMenuItemByRestaurantAndName(restaurant, ITEM_NAME);
        verify(menuItemsRepository, never()).save(any(MenuItem.class));
    }

    @Test
    public void testFetchAll_shouldReturnListOfMenuItemsInResponse_restaurantNotFoundInDatabase_throwsNoSuchElementException() {
        when(restaurantService.findById(VALID_RESTAURANT_ID)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> menuItemsService.fetchAll(VALID_RESTAURANT_ID));

        verify(restaurantService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, never()).findMenuItemsByRestaurant(restaurant);
        verify(menuItemsRepository, never()).findMenuItemByRestaurantAndName(restaurant, ITEM_NAME);
        verify(menuItemsRepository, never()).save(any(MenuItem.class));
    }

    @Test
    public void testFetchAll_shouldReturnListOfMenuItemsInResponse_unknownErrorFromDatabase_throwsRuntimeException() {
        when(restaurantService.findById(VALID_RESTAURANT_ID)).thenThrow(DataRetrievalFailureException.class);

        assertThrows(RuntimeException.class, () -> menuItemsService.fetchAll(VALID_RESTAURANT_ID));

        verify(restaurantService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(menuItemsRepository, never()).findMenuItemsByRestaurant(restaurant);
        verify(menuItemsRepository, never()).findMenuItemByRestaurantAndName(restaurant, ITEM_NAME);
        verify(menuItemsRepository, never()).save(any(MenuItem.class));
    }
}