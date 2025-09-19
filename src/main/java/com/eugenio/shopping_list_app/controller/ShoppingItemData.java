package com.eugenio.shopping_list_app.controller;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ShoppingItemData(
    @NotBlank(message = "Name is required")
    String name, 
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be greater than or equal to zero")
    BigDecimal price, 
    
    @Min(value = 1, message = "Quantity must be greater than zero")
    int quantity, 
    
    @NotBlank(message = "Category is required")
    String category
) {
}