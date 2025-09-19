package com.eugenio.shopping_list_app.service;

import com.eugenio.shopping_list_app.repository.ShoppingItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ShoppingItemService {

    ShoppingItem saveOrUpdate(ShoppingItem shoppingItem);

    Optional<ShoppingItem> get(long id);

    void delete(long id);

    Page<ShoppingItem> find(Pageable pageable);
}
