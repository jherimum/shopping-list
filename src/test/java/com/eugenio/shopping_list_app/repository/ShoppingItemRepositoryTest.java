package com.eugenio.shopping_list_app.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ShoppingItemRepositoryTest {

    @Autowired
    private ShoppingItemRepository shoppingItemRepository;



}