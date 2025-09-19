package com.eugenio.shopping_list_app.controller;

import com.eugenio.shopping_list_app.repository.ShoppingItem;
import com.eugenio.shopping_list_app.service.ShoppingItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.function.Function;

@RestController
@RequestMapping("/shopping-items")
@Tag(name = "Shopping Items", description = "Operations for managing shopping items")
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
    @Operation(summary = "Get all shopping items", description = "Retrieve a paginated list of all shopping items")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved shopping items")
    })
    public ResponseEntity<Page<ShoppingItem>> query(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int size){
        if (page < 0 || size < 1) {
            return ResponseEntity.badRequest().build();
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(this.shoppingItemService.find(pageRequest));
    }

    @PostMapping
    @Operation(summary = "Create a shopping item", description = "Create a new shopping item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Shopping item created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ShoppingItem> create(
            @Parameter(description = "Shopping item data to create") @Valid @RequestBody ShoppingItemData request){
        var saved = this.shoppingItemService.saveOrUpdate(TO_ENTITY.apply(request));
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get shopping item by ID", description = "Retrieve a specific shopping item by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Shopping item found"),
        @ApiResponse(responseCode = "404", description = "Shopping item not found")
    })
    public ResponseEntity<ShoppingItem> get(
            @Parameter(description = "Shopping item ID") @PathVariable("id") long id) {
        return ResponseEntity.ok(retrieveShoppingItem(id));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete shopping item", description = "Delete a shopping item by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Shopping item deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Shopping item not found")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Shopping item ID") @PathVariable("id") long id) {
        this.shoppingItemService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Update shopping item", description = "Update an existing shopping item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Shopping item updated successfully"),
        @ApiResponse(responseCode = "404", description = "Shopping item not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<ShoppingItem> update(
            @Parameter(description = "Shopping item ID") @PathVariable("id") long id, 
            @Parameter(description = "Updated shopping item data") @Valid @RequestBody ShoppingItemData request) {
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
