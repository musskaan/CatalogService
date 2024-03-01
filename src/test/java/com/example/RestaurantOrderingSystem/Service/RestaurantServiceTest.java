package com.example.RestaurantOrderingSystem.Service;

import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.ListRestaurantsResponse;
import com.example.RestaurantOrderingSystem.Repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.RestaurantOrderingSystem.Constants.Constants.restaurant;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;


    @Test
    public void testFetchAll_shouldReturnListOfWallets_success() {
        List<Restaurant> expectedRestaurants = List.of(restaurant);
        when(restaurantRepository.findAll()).thenReturn(expectedRestaurants);

        ListRestaurantsResponse actualResponse = restaurantService.fetchAll();

        List<Restaurant> actualRestaurants = actualResponse.getRestaurants();
        assertEquals(expectedRestaurants.size(), actualRestaurants.size());
        assertEquals(expectedRestaurants.getFirst().getName(), actualRestaurants.getFirst().getName());
        assertEquals(expectedRestaurants.getFirst().getMenuItems(), actualRestaurants.getFirst().getMenuItems());
        assertEquals(expectedRestaurants.getFirst().getAddress(), actualRestaurants.getFirst().getAddress());
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    public void testFetchAll_shouldReturnListOfWallets_unknownRepositoryError_throwsRuntimeException() {
        when(restaurantRepository.findAll()).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> restaurantService.fetchAll());
        verify(restaurantRepository, times(1)).findAll();
    }
}