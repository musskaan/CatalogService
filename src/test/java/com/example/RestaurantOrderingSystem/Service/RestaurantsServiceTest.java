package com.example.RestaurantOrderingSystem.Service;

import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Model.CreateRestaurantResponse;
import com.example.RestaurantOrderingSystem.Model.ListRestaurantsResponse;
import com.example.RestaurantOrderingSystem.Repository.RestaurantsRepository;
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
class RestaurantsServiceTest {

    @Mock
    private RestaurantsRepository restaurantsRepository;

    @InjectMocks
    private RestaurantsService restaurantsService;


    @Test
    public void testFetchAll_shouldReturnListOfWallets_success() {
        List<Restaurant> expectedRestaurants = List.of(restaurant);
        when(restaurantsRepository.findAll()).thenReturn(expectedRestaurants);

        ListRestaurantsResponse actualResponse = restaurantsService.fetchAll();

        List<Restaurant> actualRestaurants = actualResponse.getRestaurants();
        assertEquals(expectedRestaurants.size(), actualRestaurants.size());
        assertEquals(expectedRestaurants.getFirst().getName(), actualRestaurants.getFirst().getName());
        assertEquals(expectedRestaurants.getFirst().getAddress(), actualRestaurants.getFirst().getAddress());
        verify(restaurantsRepository, times(1)).findAll();
        verify(restaurantsRepository, never()).save(any(Restaurant.class));
        verify(restaurantsRepository, never()).findById(VALID_RESTAURANT_ID);
    }

    @Test
    public void testFetchAll_shouldReturnListOfWallets_unknownRepositoryError_throwsRuntimeException() {
        when(restaurantsRepository.findAll()).thenThrow(DataRetrievalFailureException.class);

        assertThrows(RuntimeException.class, () -> restaurantsService.fetchAll());

        verify(restaurantsRepository, times(1)).findAll();
        verify(restaurantsRepository, never()).save(any(Restaurant.class));
        verify(restaurantsRepository, never()).findById(VALID_RESTAURANT_ID);
    }

    @Test
    public void testCreate_shouldReturnCreatedRestaurant_success() {
        when(restaurantsRepository.save(restaurant)).thenReturn(restaurant);

        CreateRestaurantResponse actualResponse = restaurantsService.create(restaurant);

        Restaurant actualRestaurant = actualResponse.getRestaurant();
        assertEquals(restaurant.getName(), actualRestaurant.getName());
        assertEquals(restaurant.getAddress(), actualRestaurant.getAddress());
        verify(restaurantsRepository, times(1)).save(any(Restaurant.class));
        verify(restaurantsRepository, never()).findAll();
        verify(restaurantsRepository, never()).findById(VALID_RESTAURANT_ID);
    }

    @Test
    public void testCreate_shouldReturnCreatedRestaurant_unknownRepositoryError_throwsRuntimeException() {
        when(restaurantsRepository.save(restaurant)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> restaurantsService.create(restaurant));

        verify(restaurantsRepository, times(1)).save(any(Restaurant.class));
        verify(restaurantsRepository, never()).findAll();
        verify(restaurantsRepository, never()).findById(VALID_RESTAURANT_ID);
    }

    @Test
    public void testFindById_shouldReturnRestaurant_restaurantNotFound_throwsNoSuchElementException() {
        when(restaurantsRepository.findById(VALID_RESTAURANT_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> restaurantsService.findById(VALID_RESTAURANT_ID));

        verify(restaurantsRepository, times(1)).findById(VALID_RESTAURANT_ID);
        verify(restaurantsRepository, never()).findAll();
        verify(restaurantsRepository, never()).save(any(Restaurant.class));
    }

    @Test
    public void testFindById_shouldReturnRestaurant_success() {
        when(restaurantsRepository.findById(VALID_RESTAURANT_ID)).thenReturn(Optional.of(restaurant));

        Restaurant actualRestaurant = restaurantsService.findById(VALID_RESTAURANT_ID);

        assertEquals(actualRestaurant, restaurant);
        verify(restaurantsRepository, times(1)).findById(VALID_RESTAURANT_ID);
        verify(restaurantsRepository, never()).findAll();
        verify(restaurantsRepository, never()).save(any(Restaurant.class));
    }
}