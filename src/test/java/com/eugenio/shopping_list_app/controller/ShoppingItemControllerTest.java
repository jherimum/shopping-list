package com.eugenio.shopping_list_app.controller;

import com.eugenio.shopping_list_app.repository.ShoppingItem;
import com.eugenio.shopping_list_app.service.ShoppingItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;


class ShoppingItemControllerTest {

    @Mock
    private ShoppingItemService shoppingItemService;

    @InjectMocks
    private ShoppingItemController shoppingItemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_get_should_throw_exception_when_shopping_item_not_found() {
        Mockito.when(shoppingItemService.get(Mockito.anyLong())).thenReturn(Optional.empty());
        RestException.NotFoundException exception = Assertions.assertThrows(RestException.NotFoundException.class, () -> {
            shoppingItemController.get(1);
        });
    }


    @Test
    void test_get_return_shopping_item_when_found() {
        var shoppingItem = ShoppingItem.builder().name("item1").price(BigDecimal.valueOf(10)).category("category").quantity(1).build();
        Mockito.when(shoppingItemService.get(Mockito.anyLong())).thenReturn(Optional.of(shoppingItem));
        Assertions.assertEquals(ResponseEntity.ok(shoppingItem), shoppingItemController.get(1));
    }

    @Test
    void test_get_should_throw_exception_when_shopping_item_was_not_deleted() {
        Mockito.when(shoppingItemService.delete(Mockito.anyLong())).thenReturn(false);
        RestException.NotFoundException exception = Assertions.assertThrows(RestException.NotFoundException.class, () -> {
            shoppingItemController.delete(1);
        });
    }

    @Test
    void test_get_shoudl_return_ok_when_deleted() {
        Mockito.when(shoppingItemService.delete(Mockito.anyLong())).thenReturn(true);
        Assertions.assertEquals(ResponseEntity.ok().build(), shoppingItemController.delete(1));
    }


}