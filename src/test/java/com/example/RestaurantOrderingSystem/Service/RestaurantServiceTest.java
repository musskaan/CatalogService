package com.example.RestaurantOrderingSystem.Service;

import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.CreateRestaurantResponse;
import com.example.RestaurantOrderingSystem.Model.ListRestaurantsResponse;
import com.example.RestaurantOrderingSystem.Repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataRetrievalFailureException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.RestaurantOrderingSystem.Constants.Constants.VALID_RESTAURANT_ID;
import static com.example.RestaurantOrderingSystem.Constants.Constants.restaurant;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertEquals(expectedRestaurants.getFirst().getAddress(), actualRestaurants.getFirst().getAddress());
        verify(restaurantRepository, times(1)).findAll();
        verify(restaurantRepository, never()).save(any(Restaurant.class));
        verify(restaurantRepository, never()).findById(VALID_RESTAURANT_ID);
    }

    @Test
    public void testFetchAll_shouldReturnListOfWallets_unknownRepositoryError_throwsRuntimeException() {
        when(restaurantRepository.findAll()).thenThrow(DataRetrievalFailureException.class);

        assertThrows(RuntimeException.class, () -> restaurantService.fetchAll());

        verify(restaurantRepository, times(1)).findAll();
        verify(restaurantRepository, never()).save(any(Restaurant.class));
        verify(restaurantRepository, never()).findById(VALID_RESTAURANT_ID);
    }

    @Test
    public void testCreate_shouldReturnCreatedRestaurant_success() {
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        CreateRestaurantResponse actualResponse = restaurantService.create(restaurant);

        Restaurant actualRestaurant = actualResponse.getRestaurant();
        assertEquals(restaurant.getName(), actualRestaurant.getName());
        assertEquals(restaurant.getAddress(), actualRestaurant.getAddress());
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
        verify(restaurantRepository, never()).findAll();
        verify(restaurantRepository, never()).findById(VALID_RESTAURANT_ID);
    }

    @Test
    public void testCreate_shouldReturnCreatedRestaurant_unknownRepositoryError_throwsRuntimeException() {
        when(restaurantRepository.save(restaurant)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> restaurantService.create(restaurant));

        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
        verify(restaurantRepository, never()).findAll();
        verify(restaurantRepository, never()).findById(VALID_RESTAURANT_ID);
    }

    @Test
    public void testFindById_shouldReturnRestaurant_restaurantNotFound_throwsNoSuchElementException() {
        when(restaurantRepository.findById(VALID_RESTAURANT_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> restaurantService.findById(VALID_RESTAURANT_ID));

        verify(restaurantRepository, times(1)).findById(VALID_RESTAURANT_ID);
        verify(restaurantRepository, never()).findAll();
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    public void testFindById_shouldReturnRestaurant_success() {
        when(restaurantRepository.findById(VALID_RESTAURANT_ID)).thenReturn(Optional.of(restaurant));

        Restaurant actualRestaurant = restaurantService.findById(VALID_RESTAURANT_ID);

        assertEquals(actualRestaurant, restaurant);
        verify(restaurantRepository, times(1)).findById(VALID_RESTAURANT_ID);
        verify(restaurantRepository, never()).findAll();
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }
}