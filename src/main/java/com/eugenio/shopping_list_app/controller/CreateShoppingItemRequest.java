package com.eugenio.shopping_list_app.controller;

import java.math.BigDecimal;

public record CreateShoppingItemRequest(String name, BigDecimal price, int quantity, String category) {
}