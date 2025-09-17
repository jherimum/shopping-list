package com.eugenio.shopping_list_app.controller;

import com.eugenio.shopping_list_app.repository.ShoppingItem;
import com.eugenio.shopping_list_app.service.ShoppingItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("shopping-items")
public class ShoppingItemController {

    private final ShoppingItemService shoppingItemService;

    @Autowired
    public ShoppingItemController(ShoppingItemService shoppingItemService) {
        this.shoppingItemService = shoppingItemService;
    }

    @GetMapping("{id}")
    public ResponseEntity<ShoppingItem> get(@PathVariable("id") long id) {
        return shoppingItemService.get(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RestException.NotFoundException(String.format("Shopping item with id: %s was not found", id)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        // the get method was not used to avoid two database calls
        var deleted = this.shoppingItemService.delete(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        throw new RestException.NotFoundException(String.format("Shopping item with id: %s was not found", id));
    }


}
