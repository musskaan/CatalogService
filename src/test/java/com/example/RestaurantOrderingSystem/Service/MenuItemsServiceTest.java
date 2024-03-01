package com.example.RestaurantOrderingSystem.Service;

import com.example.RestaurantOrderingSystem.Model.CreateMenuItemsRequest;
import com.example.RestaurantOrderingSystem.Model.CreateMenuItemsResponse;
import com.example.RestaurantOrderingSystem.Model.MenuItemDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.List;
import java.util.NoSuchElementException;

import static com.example.RestaurantOrderingSystem.Constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemsServiceTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private MenuItemsService menuItemsService;

    @Test
    public void testCreate_shouldReturnCreatedItemInResponse_success() throws Exception {
        when(restaurantService.addMenuItem(anyLong(), any(CreateMenuItemsRequest.class))).thenReturn(restaurant);

        CreateMenuItemsResponse actualResponse = menuItemsService.create(VALID_RESTAURANT_ID, createMenuItemsRequest);

        List<MenuItemDTO> menuItemDTOList = actualResponse.getMenuItems();
        assertEquals(menuItemDTOList.getFirst().getRestaurantName(), restaurant.getName());
        assertEquals(menuItemDTOList.getFirst().getItemName(), restaurant.getMenuItems().getFirst().getName());
        assertEquals(menuItemDTOList.getFirst().getPrice(), restaurant.getMenuItems().getFirst().getPrice());
        verify(restaurantService, times(1)).addMenuItem(VALID_RESTAURANT_ID, createMenuItemsRequest);
    }

    @Test
    public void testCreate_restaurantNotFoundInDatabase_throwsNoSuchElementException() throws Exception {
        when(restaurantService.addMenuItem(anyLong(), any(CreateMenuItemsRequest.class))).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> menuItemsService.create(VALID_RESTAURANT_ID, createMenuItemsRequest));

        verify(restaurantService, times(1)).addMenuItem(VALID_RESTAURANT_ID, createMenuItemsRequest);
    }

    @Test
    public void testCreate_unknownErrorFromDatabase_throwsRuntimeException() throws Exception {
        when(restaurantService.addMenuItem(anyLong(), any(CreateMenuItemsRequest.class))).thenThrow(DataRetrievalFailureException.class);

        assertThrows(RuntimeException.class, () -> menuItemsService.create(VALID_RESTAURANT_ID, createMenuItemsRequest));

        verify(restaurantService, times(1)).addMenuItem(VALID_RESTAURANT_ID, createMenuItemsRequest);
    }
}