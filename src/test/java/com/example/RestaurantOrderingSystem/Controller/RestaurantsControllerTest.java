package com.example.RestaurantOrderingSystem.Controller;

import com.example.RestaurantOrderingSystem.Config.SecurityConfig;
import com.example.RestaurantOrderingSystem.Entity.Restaurant;
import com.example.RestaurantOrderingSystem.Service.RestaurantService;
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

import static com.example.RestaurantOrderingSystem.Constants.Constants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class RestaurantsControllerTest {

    @MockBean
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantsController restaurantsController;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testFetchAll_shouldReturnListOfRestaurantsInResponse_returnsIsOk() throws Exception {
        when(restaurantService.fetchAll()).thenReturn(listRestaurantsResponse);

        mockMvc.perform(get("/api/v1/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS_MESSAGE));

        verify(restaurantService, times(1)).fetchAll();
        verify(restaurantService, never()).create(any(Restaurant.class));
    }

    @Test
    void testFetchAll_shouldReturnListOfRestaurantsInResponse_unknownDatabaseError_returnsInternalServerError() throws Exception {
        when(restaurantService.fetchAll()).thenThrow(new DataRetrievalFailureException("Error querying restaurants from database"));

        mockMvc.perform(get("/api/v1/restaurants"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error querying restaurants from database"));

        verify(restaurantService, times(1)).fetchAll();
        verify(restaurantService, never()).create(any(Restaurant.class));
    }

    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void testCreate_whenAuthorizedUser_shouldReturnCreatedRestaurantInResponse_returnsIsCreated() throws Exception {
        when(restaurantService.create(any(Restaurant.class))).thenReturn(createRestaurantResponse);

        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(SUCCESS_MESSAGE));

        verify(restaurantService, times(1)).create(any(Restaurant.class));
        verify(restaurantService, never()).fetchAll();
    }

    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void testCreate_whenAuthorizedUser_shouldReturnCreatedRestaurantInResponse_unknownDatabaseError_returnsInternalServerError() throws Exception {
        when(restaurantService.create(any(Restaurant.class))).thenThrow(new RuntimeException("Error saving to database"));

        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error saving to database"));

        verify(restaurantService, times(1)).create(any(Restaurant.class));
        verify(restaurantService, never()).fetchAll();
    }

    @Test
    @WithAnonymousUser
    void testCreate_whenUnauthorizedUser_returnsIsUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isUnauthorized());

        verify(restaurantService, never()).create(any(Restaurant.class));
        verify(restaurantService, never()).fetchAll();
    }
}