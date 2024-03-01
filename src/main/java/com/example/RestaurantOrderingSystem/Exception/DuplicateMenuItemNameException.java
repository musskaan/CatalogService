package com.example.RestaurantOrderingSystem.Exception;

public class DuplicateMenuItemNameException extends RuntimeException {
    public DuplicateMenuItemNameException(String message) {
        super(message);
    }
}
