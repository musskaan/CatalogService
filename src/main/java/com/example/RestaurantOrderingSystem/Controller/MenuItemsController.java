package com.example.RestaurantOrderingSystem.Controller;

import com.example.RestaurantOrderingSystem.Exception.DuplicateMenuItemNameException;
import com.example.RestaurantOrderingSystem.Model.*;
import com.example.RestaurantOrderingSystem.Service.MenuItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/restaurants/{restaurantId}/menuItems")
public class MenuItemsController {

    @Autowired
    private MenuItemsService menuItemsService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@PathVariable Long restaurantId, @RequestBody CreateMenuItemsRequest createMenuItemsRequest) {
        try {
            CreateMenuItemsResponse createMenuItemResponse = menuItemsService.create(restaurantId, createMenuItemsRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createMenuItemResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        } catch (DuplicateMenuItemNameException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping
    public ResponseEntity<?> fetchAll(@PathVariable Long restaurantId) {
        try {
            ListMenuItemsResponse listMenuItemsResponse = menuItemsService.fetchAll(restaurantId);
            return ResponseEntity.status(HttpStatus.OK).body(listMenuItemsResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @GetMapping("/{menuItemName}")
    public ResponseEntity<?> fetchByName(@PathVariable(name = "restaurantId") String restaurantId, @PathVariable(name = "menuItemName") String menuItemName) {
        try {
            ApiResponse apiResponse = menuItemsService.fetchByName(restaurantId, menuItemName);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
