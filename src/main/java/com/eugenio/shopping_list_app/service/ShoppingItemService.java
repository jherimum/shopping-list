package com.eugenio.shopping_list_app.service;

import com.eugenio.shopping_list_app.repository.ShoppingItem;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ShoppingItemService {

    Optional<ShoppingItem> get(long id);

    boolean delete(long id);
}
