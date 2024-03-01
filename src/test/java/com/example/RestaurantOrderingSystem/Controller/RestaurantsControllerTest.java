package com.example.RestaurantOrderingSystem.Controller;

import com.example.RestaurantOrderingSystem.Service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.RestaurantOrderingSystem.Constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantsControllerTest {

    @MockBean
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantsController restaurantsController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFetchAll_shouldReturnListOfRestaurantsInResponse_success() throws Exception {
        when(restaurantService.fetchAll()).thenReturn(listRestaurantsResponse);

        mockMvc.perform(get("/api/v1/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SUCCESS_MESSAGE));

        assertEquals(1, listRestaurantsResponse.getRestaurants().size());
        assertTrue(listRestaurantsResponse.getRestaurants().contains(restaurant));
        assertEquals(SUCCESS_MESSAGE, listRestaurantsResponse.getMessage());
        verify(restaurantService, times(1)).fetchAll();
    }

    @Test
    void testFetchAll_shouldReturnListOfRestaurantsInResponse_unknownDatabaseError_returnsInternalServerError() throws Exception {
        when(restaurantService.fetchAll()).thenThrow(new DataRetrievalFailureException("Error querying restaurants from database"));

        mockMvc.perform(get("/api/v1/restaurants"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error querying restaurants from database"));

        verify(restaurantService, times(1)).fetchAll();
    }
}