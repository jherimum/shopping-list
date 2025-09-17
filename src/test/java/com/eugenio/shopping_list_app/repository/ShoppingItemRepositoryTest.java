package com.eugenio.shopping_list_app.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ShoppingItemRepositoryTest {

    @Autowired
    private ShoppingItemRepository shoppingItemRepository;

    @Test
    void test_delete() {
        var deleted_count = shoppingItemRepository.delete(1);
        assertEquals(0, deleted_count);

        var shoppingItem = ShoppingItem.builder().build();
        var saved = this.shoppingItemRepository.save(shoppingItem);

        deleted_count = shoppingItemRepository.delete(saved.getId());
        assertEquals(1, deleted_count);

        assertEquals(0, shoppingItemRepository.count());
    }


}