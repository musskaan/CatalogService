package com.example.RestaurantOrderingSystem.Controller;

import com.example.RestaurantOrderingSystem.Config.SecurityConfig;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Service.RestaurantsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static com.example.RestaurantOrderingSystem.Constants.Constants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class RestaurantsControllerTest {

    @MockBean
    private RestaurantsService restaurantsService;

    @InjectMocks
    private RestaurantsController restaurantsController;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testFetchAll_shouldReturnListOfRestaurantsInResponse_returnsIsOk() throws Exception {
        when(restaurantsService.fetchAll()).thenReturn(listRestaurantsResponse);

        mockMvc.perform(get("/api/v1/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.restaurants[0].name").value(listRestaurantsResponse.getRestaurants().getFirst().getName()))
                .andExpect(jsonPath("$.restaurants[0].address.street").value(listRestaurantsResponse.getRestaurants().getFirst().getAddress().getStreet()))
                .andExpect(jsonPath("$.restaurants[0].address.city").value(listRestaurantsResponse.getRestaurants().getFirst().getAddress().getCity()))
                .andExpect(jsonPath("$.restaurants[0].address.state").value(listRestaurantsResponse.getRestaurants().getFirst().getAddress().getState()))
                .andExpect(jsonPath("$.restaurants[0].address.zipCode").value(listRestaurantsResponse.getRestaurants().getFirst().getAddress().getZipCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS_MESSAGE));

        verify(restaurantsService, times(1)).fetchAll();
        verify(restaurantsService, never()).create(any(Restaurant.class));
        verify(restaurantsService, never()).findById(anyLong());
    }

    @Test
    void testFetchAll_shouldReturnListOfRestaurantsInResponse_unknownDatabaseError_returnsInternalServerError() throws Exception {
        when(restaurantsService.fetchAll()).thenThrow(new DataRetrievalFailureException("Error querying restaurants from database"));

        mockMvc.perform(get("/api/v1/restaurants"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Error querying restaurants from database"));

        verify(restaurantsService, times(1)).fetchAll();
        verify(restaurantsService, never()).create(any(Restaurant.class));
        verify(restaurantsService, never()).findById(anyLong());
    }

    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void testCreate_whenAuthorizedUser_shouldReturnCreatedRestaurantInResponse_returnsIsCreated() throws Exception {
        when(restaurantsService.create(any(Restaurant.class))).thenReturn(createRestaurantResponse);

        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.restaurant.name").value(createRestaurantResponse.getRestaurant().getName()))
                .andExpect(jsonPath("$.restaurant.address.street").value(createRestaurantResponse.getRestaurant().getAddress().getStreet()))
                .andExpect(jsonPath("$.restaurant.address.city").value(createRestaurantResponse.getRestaurant().getAddress().getCity()))
                .andExpect(jsonPath("$.restaurant.address.state").value(createRestaurantResponse.getRestaurant().getAddress().getState()))
                .andExpect(jsonPath("$.restaurant.address.zipCode").value(createRestaurantResponse.getRestaurant().getAddress().getZipCode()))
                .andExpect(jsonPath("$.message").value(SUCCESS_MESSAGE));

        verify(restaurantsService, times(1)).create(any(Restaurant.class));
        verify(restaurantsService, never()).fetchAll();
        verify(restaurantsService, never()).findById(anyLong());
    }

    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void testCreate_whenAuthorizedUser_shouldReturnCreatedRestaurantInResponse_unknownDatabaseError_returnsInternalServerError() throws Exception {
        when(restaurantsService.create(any(Restaurant.class))).thenThrow(new RuntimeException("Error saving to database"));

        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Error saving to database"));

        verify(restaurantsService, times(1)).create(any(Restaurant.class));
        verify(restaurantsService, never()).fetchAll();
        verify(restaurantsService, never()).findById(anyLong());
    }

    @Test
    @WithAnonymousUser
    void testCreate_whenUnauthorizedUser_returnsIsUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isUnauthorized());

        verify(restaurantsService, never()).create(any(Restaurant.class));
        verify(restaurantsService, never()).fetchAll();
        verify(restaurantsService, never()).findById(anyLong());
    }

    @Test
    void testFetchById_shouldReturnRestaurant_returnsIsOk() throws Exception {
        when(restaurantsService.findById(VALID_RESTAURANT_ID)).thenReturn(restaurant);

        mockMvc.perform(get("/api/v1/restaurants/{id}", VALID_RESTAURANT_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(restaurant)));

        verify(restaurantsService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(restaurantsService, never()).fetchAll();
        verify(restaurantsService, never()).create(any(Restaurant.class));
    }

    @Test
    void testFetchById_noRestaurantFoundWithGivenId_returnsNotFound() throws Exception {
        when(restaurantsService.findById(VALID_RESTAURANT_ID)).thenThrow(new NoSuchElementException("Restaurant not found with id: " + VALID_RESTAURANT_ID));

        mockMvc.perform(get("/api/v1/restaurants/{id}", VALID_RESTAURANT_ID.toString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Restaurant not found with id: " + VALID_RESTAURANT_ID));

        verify(restaurantsService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(restaurantsService, never()).fetchAll();
        verify(restaurantsService, never()).create(any(Restaurant.class));
    }

    @Test
    void testFetchById_unexpectedDatabaseError_returnsInternalServerError() throws Exception {
        when(restaurantsService.findById(VALID_RESTAURANT_ID)).thenThrow(new DataRetrievalFailureException("Error fetching restaurant with id: " + VALID_RESTAURANT_ID));

        mockMvc.perform(get("/api/v1/restaurants/{id}", VALID_RESTAURANT_ID.toString()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Error fetching restaurant with id: " + VALID_RESTAURANT_ID));

        verify(restaurantsService, times(1)).findById(VALID_RESTAURANT_ID);
        verify(restaurantsService, never()).fetchAll();
        verify(restaurantsService, never()).create(any(Restaurant.class));
    }
}