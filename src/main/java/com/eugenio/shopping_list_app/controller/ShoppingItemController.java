package com.eugenio.shopping_list_app.controller;

import com.eugenio.shopping_list_app.repository.ShoppingItem;
import com.eugenio.shopping_list_app.service.ShoppingItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import java.util.function.Function;

@RestController
@RequestMapping("/shopping-items")  // 👈 base path for this controller
public class ShoppingItemController {

    private static final Function<ShoppingItemData, ShoppingItem> TO_ENTITY = (req) -> {
        return new ShoppingItem(req.name(), req.price(), req.quantity(), req.category());
    };

    private final ShoppingItemService shoppingItemService;

    @Autowired
    public ShoppingItemController(ShoppingItemService shoppingItemService) {
        this.shoppingItemService = shoppingItemService;
    }

    @GetMapping
    public ResponseEntity<Page<ShoppingItem>> query( Pageable pageable){
        return ResponseEntity.ok(this.shoppingItemService.find(pageable));
    }

    @PostMapping
    public ResponseEntity<ShoppingItem> create(@RequestBody ShoppingItemData request){
        var saved = this.shoppingItemService.saveOrUpdate(TO_ENTITY.apply(request));
        var link = linkTo(methodOn(ShoppingItemController.class).get(saved.getId()));
        return ResponseEntity.created(link.toUri()).body(saved);
    }

    @GetMapping("{id}")
    public ResponseEntity<ShoppingItem> get(@PathVariable("id") long id) {
        return ResponseEntity.ok(retrieveShoppingItem(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        this.shoppingItemService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ShoppingItem> update(@PathVariable("id") long id, @RequestBody ShoppingItemData request) {
        var shoppingItem = retrieveShoppingItem(id);
        shoppingItem.setCategory(request.category());
        shoppingItem.setName(request.name());
        shoppingItem.setPrice(request.price());
        shoppingItem.setQuantity(request.quantity());
        return ResponseEntity.ok(this.shoppingItemService.saveOrUpdate(shoppingItem));
    }

    private ShoppingItem retrieveShoppingItem(long id){
        return this.shoppingItemService.get(id)
                .orElseThrow(() -> new RestException.NotFoundException(String.format("Shopping item with id: %s was not found", id)));
    }



}
