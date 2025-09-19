package com.eugenio.shopping_list_app.controller;

import com.eugenio.shopping_list_app.repository.ShoppingItem;
import com.eugenio.shopping_list_app.repository.ShoppingItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ShoppingItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShoppingItemRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void shouldCreateShoppingItem() throws Exception {
        ShoppingItemData itemData = new ShoppingItemData(
            "Test Item", 
            BigDecimal.valueOf(9.99), 
            2, 
            "Test Category"
        );

        mockMvc.perform(post("/shopping-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemData)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Test Item")))
                .andExpect(jsonPath("$.price", is(9.99)))
                .andExpect(jsonPath("$.quantity", is(2)))
                .andExpect(jsonPath("$.category", is("Test Category")))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.cost", is(19.98)))
                .andExpect(jsonPath("$.createdAt", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedAt", notNullValue()));
    }

    @Test
    void shouldGetShoppingItemById() throws Exception {
        ShoppingItem item = new ShoppingItem("Test Item", BigDecimal.valueOf(5.50), 3, "Category A");
        ShoppingItem savedItem = repository.save(item);

        mockMvc.perform(get("/shopping-items/{id}", savedItem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedItem.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Test Item")))
                .andExpect(jsonPath("$.price", is(5.50)))
                .andExpect(jsonPath("$.quantity", is(3)))
                .andExpect(jsonPath("$.category", is("Category A")))
                .andExpect(jsonPath("$.cost", is(16.50)));
    }

    @Test
    void shouldReturnNotFoundForNonExistentItem() throws Exception {
        mockMvc.perform(get("/shopping-items/{id}", 99999))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateShoppingItem() throws Exception {
        ShoppingItem item = new ShoppingItem("Original Item", BigDecimal.valueOf(10.00), 1, "Original Category");
        ShoppingItem savedItem = repository.save(item);

        ShoppingItemData updateData = new ShoppingItemData(
            "Updated Item", 
            BigDecimal.valueOf(15.99), 
            5, 
            "Updated Category"
        );

        mockMvc.perform(put("/shopping-items/{id}", savedItem.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedItem.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Updated Item")))
                .andExpect(jsonPath("$.price", is(15.99)))
                .andExpect(jsonPath("$.quantity", is(5)))
                .andExpect(jsonPath("$.category", is("Updated Category")))
                .andExpect(jsonPath("$.cost", is(79.95)));
    }

    @Test
    void shouldDeleteShoppingItem() throws Exception {
        ShoppingItem item = new ShoppingItem("Item to Delete", BigDecimal.valueOf(7.99), 2, "Delete Category");
        ShoppingItem savedItem = repository.save(item);

        mockMvc.perform(delete("/shopping-items/{id}", savedItem.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/shopping-items/{id}", savedItem.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllShoppingItemsWithDefaultPagination() throws Exception {
        for (int i = 1; i <= 25; i++) {
            repository.save(new ShoppingItem("Item " + i, BigDecimal.valueOf(i), i, "Category " + (i % 3)));
        }

        mockMvc.perform(get("/shopping-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(20)))
                .andExpect(jsonPath("$.totalElements", is(25)))
                .andExpect(jsonPath("$.totalPages", is(2)))
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.size", is(20)))
                .andExpect(jsonPath("$.first", is(true)))
                .andExpect(jsonPath("$.last", is(false)));
    }

    @Test
    void shouldGetShoppingItemsWithCustomPagination() throws Exception {
        for (int i = 1; i <= 15; i++) {
            repository.save(new ShoppingItem("Item " + i, BigDecimal.valueOf(i), 1, "Category"));
        }

        mockMvc.perform(get("/shopping-items")
                .param("page", "1")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.totalElements", is(15)))
                .andExpect(jsonPath("$.totalPages", is(3)))
                .andExpect(jsonPath("$.number", is(1)))
                .andExpect(jsonPath("$.size", is(5)))
                .andExpect(jsonPath("$.first", is(false)))
                .andExpect(jsonPath("$.last", is(false)));
    }

    @Test
    void shouldGetLastPageCorrectly() throws Exception {
        for (int i = 1; i <= 12; i++) {
            repository.save(new ShoppingItem("Item " + i, BigDecimal.valueOf(i), 1, "Category"));
        }

        mockMvc.perform(get("/shopping-items")
                .param("page", "2")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.totalElements", is(12)))
                .andExpect(jsonPath("$.totalPages", is(3)))
                .andExpect(jsonPath("$.number", is(2)))
                .andExpect(jsonPath("$.size", is(5)))
                .andExpect(jsonPath("$.first", is(false)))
                .andExpect(jsonPath("$.last", is(true)));
    }

    @Test
    void shouldReturnEmptyPageWhenNoItems() throws Exception {
        mockMvc.perform(get("/shopping-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(0)))
                .andExpect(jsonPath("$.totalElements", is(0)))
                .andExpect(jsonPath("$.totalPages", is(0)))
                .andExpect(jsonPath("$.empty", is(true)));
    }

    @Test
    void shouldHandleInvalidPageParameters() throws Exception {
        repository.save(new ShoppingItem("Test Item", BigDecimal.valueOf(10), 1, "Category"));

        mockMvc.perform(get("/shopping-items")
                .param("page", "-1")
                .param("size", "10"))
                .andExpect(status().isBadRequest());
        
        mockMvc.perform(get("/shopping-items")
                .param("page", "0")
                .param("size", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldValidateRequiredFieldsOnCreate() throws Exception {
        ShoppingItemData invalidData = new ShoppingItemData(null, null, 0, null);

        mockMvc.perform(post("/shopping-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidData)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Validation failed")))
                .andExpect(jsonPath("$.message", is("The request contains invalid data. Please check the fields and try again.")))
                .andExpect(jsonPath("$.fieldErrors.name", is("Name is required")))
                .andExpect(jsonPath("$.fieldErrors.price", is("Price is required")))
                .andExpect(jsonPath("$.fieldErrors.quantity", is("Quantity must be greater than zero")))
                .andExpect(jsonPath("$.fieldErrors.category", is("Category is required")));
    }

    @Test
    void shouldValidateBlankNameOnCreate() throws Exception {
        ShoppingItemData invalidData = new ShoppingItemData("", BigDecimal.valueOf(10), 1, "Category");

        mockMvc.perform(post("/shopping-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldValidateBlankCategoryOnCreate() throws Exception {
        ShoppingItemData invalidData = new ShoppingItemData("Item", BigDecimal.valueOf(10), 1, "");

        mockMvc.perform(post("/shopping-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldValidateNegativePriceOnCreate() throws Exception {
        ShoppingItemData invalidData = new ShoppingItemData("Item", BigDecimal.valueOf(-1), 1, "Category");

        mockMvc.perform(post("/shopping-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidData)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Validation failed")))
                .andExpect(jsonPath("$.fieldErrors.price", is("Price must be greater than or equal to zero")));
    }

    @Test
    void shouldValidateZeroQuantityOnCreate() throws Exception {
        ShoppingItemData invalidData = new ShoppingItemData("Item", BigDecimal.valueOf(10), 0, "Category");

        mockMvc.perform(post("/shopping-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldValidateNegativeQuantityOnCreate() throws Exception {
        ShoppingItemData invalidData = new ShoppingItemData("Item", BigDecimal.valueOf(10), -1, "Category");

        mockMvc.perform(post("/shopping-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAllowZeroPriceOnCreate() throws Exception {
        ShoppingItemData validData = new ShoppingItemData("Free Item", BigDecimal.ZERO, 1, "Free Category");

        mockMvc.perform(post("/shopping-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validData)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Free Item")))
                .andExpect(jsonPath("$.price", is(0)));
    }

    @Test
    void shouldValidateRequiredFieldsOnUpdate() throws Exception {
        ShoppingItem item = repository.save(new ShoppingItem("Test Item", BigDecimal.valueOf(10), 1, "Category"));
        ShoppingItemData invalidData = new ShoppingItemData(null, null, 0, null);

        mockMvc.perform(put("/shopping-items/{id}", item.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldValidateNegativePriceOnUpdate() throws Exception {
        ShoppingItem item = repository.save(new ShoppingItem("Test Item", BigDecimal.valueOf(10), 1, "Category"));
        ShoppingItemData invalidData = new ShoppingItemData("Item", BigDecimal.valueOf(-5), 1, "Category");

        mockMvc.perform(put("/shopping-items/{id}", item.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldValidateZeroQuantityOnUpdate() throws Exception {
        ShoppingItem item = repository.save(new ShoppingItem("Test Item", BigDecimal.valueOf(10), 1, "Category"));
        ShoppingItemData invalidData = new ShoppingItemData("Item", BigDecimal.valueOf(10), 0, "Category");

        mockMvc.perform(put("/shopping-items/{id}", item.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateNonExistentItem() throws Exception {
        ShoppingItemData updateData = new ShoppingItemData("Item", BigDecimal.valueOf(10), 1, "Category");

        mockMvc.perform(put("/shopping-items/{id}", 99999)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteNonExistentItem() throws Exception {
        mockMvc.perform(delete("/shopping-items/{id}", 99999))
                .andExpect(status().isOk());
    }

    @Test
    void shouldVerifyFullCrudWorkflow() throws Exception {
        ShoppingItemData createData = new ShoppingItemData("Workflow Item", BigDecimal.valueOf(12.50), 4, "Workflow Category");

        String createResponse = mockMvc.perform(post("/shopping-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createData)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ShoppingItem createdItem = objectMapper.readValue(createResponse, ShoppingItem.class);
        Long itemId = createdItem.getId();

        mockMvc.perform(get("/shopping-items/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Workflow Item")));

        ShoppingItemData updateData = new ShoppingItemData("Updated Workflow Item", BigDecimal.valueOf(20.00), 2, "Updated Category");
        mockMvc.perform(put("/shopping-items/{id}", itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Workflow Item")))
                .andExpect(jsonPath("$.cost", is(40.00)));

        mockMvc.perform(delete("/shopping-items/{id}", itemId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/shopping-items/{id}", itemId))
                .andExpect(status().isNotFound());
    }
}
