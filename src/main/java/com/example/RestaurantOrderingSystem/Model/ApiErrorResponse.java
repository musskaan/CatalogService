package com.example.RestaurantOrderingSystem.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    private String message;
    private int status;
}
