package com.eugenio.shopping_list_app.repository;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoppingItemTest {

    @Test
    void test_cost() {
        var shopping_item = ShoppingItem.builder().name("name").category("category").quantity(10).price(BigDecimal.valueOf(5.32)).build();
        assertEquals(BigDecimal.valueOf(53.2).setScale(2), shopping_item.getCost());
    }

}