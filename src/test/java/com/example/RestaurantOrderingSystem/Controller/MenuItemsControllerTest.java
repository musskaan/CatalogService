package com.example.RestaurantOrderingSystem.Controller;

import com.example.RestaurantOrderingSystem.Config.SecurityConfig;
import com.example.RestaurantOrderingSystem.Service.MenuItemsService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class MenuItemsControllerTest {

    @MockBean
    private MenuItemsService menuItemsService;

    @InjectMocks
    private MenuItemsController menuItemsController;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    public void testCreate_whenAuthorizedUser_shouldReturnCreatedMenuItemInResponse_returnsIsCreated() throws Exception {
        when(menuItemsService.create(VALID_RESTAURANT_ID, createMenuItemsRequest)).thenReturn(createMenuItemsResponse);

        mockMvc.perform(post("/api/v1/restaurants/{restaurantId}/menuItems", VALID_RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createMenuItemsRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.menuItems[0].itemName").value(createMenuItemsResponse.getMenuItems().getFirst().getItemName()))
                .andExpect(jsonPath("$.menuItems[0].price").value(createMenuItemsResponse.getMenuItems().getFirst().getPrice()))
                .andExpect(jsonPath("$.menuItems[0].restaurantName").value(createMenuItemsResponse.getMenuItems().getFirst().getRestaurantName()))
                .andExpect(jsonPath("$.message").value(createMenuItemsResponse.getMessage()));

        verify(menuItemsService, times(1)).create(VALID_RESTAURANT_ID, createMenuItemsRequest);
    }

    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    public void testCreate_whenAuthorizedUser_restaurantNotFound_returnsNotFound() throws Exception {
        when(menuItemsService.create(INVALID_RESTAURANT_ID, createMenuItemsRequest)).thenThrow(new NoSuchElementException("Restaurant not found with id " + INVALID_RESTAURANT_ID));

        mockMvc.perform(post("/api/v1/restaurants/{restaurantId}/menuItems", INVALID_RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createMenuItemsRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Restaurant not found with id " + INVALID_RESTAURANT_ID));

        verify(menuItemsService, times(1)).create(INVALID_RESTAURANT_ID, createMenuItemsRequest);
    }

    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    public void testCreate_whenAuthorizedUser_unknownRepositoryError_returnsInternalServerError() throws Exception {
        when(menuItemsService.create(VALID_RESTAURANT_ID, createMenuItemsRequest)).thenThrow(new DataRetrievalFailureException("Error retrieving restaurant with id " + VALID_RESTAURANT_ID));

        mockMvc.perform(post("/api/v1/restaurants/{restaurantId}/menuItems", VALID_RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createMenuItemsRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Error retrieving restaurant with id " + VALID_RESTAURANT_ID));

        verify(menuItemsService, times(1)).create(VALID_RESTAURANT_ID, createMenuItemsRequest);
    }

    @Test
    @WithAnonymousUser
    public void testCreate_whenUnauthorizedUser_returnsIsUnauthorized() throws Exception {
        when(menuItemsService.create(VALID_RESTAURANT_ID, createMenuItemsRequest)).thenReturn(createMenuItemsResponse);

        mockMvc.perform(post("/api/v1/restaurants/{restaurantId}/menuItems", VALID_RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createMenuItemsRequest)))
                .andExpect(status().isUnauthorized());

        verify(menuItemsService, never()).create(VALID_RESTAURANT_ID, createMenuItemsRequest);
    }
}