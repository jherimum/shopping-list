package com.eugenio.shopping_list_app.service;

import com.eugenio.shopping_list_app.repository.ShoppingItem;
import com.eugenio.shopping_list_app.repository.ShoppingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingItemServiceImpl implements ShoppingItemService {

    private final ShoppingItemRepository shoppingItemRepository;

    @Autowired
    public ShoppingItemServiceImpl(ShoppingItemRepository shoppingItemRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
    }

    @Override
    public Optional<ShoppingItem> get(long id) {
        return shoppingItemRepository.findById(id);
    }

    @Override
    public boolean delete(long id) {
        return this.shoppingItemRepository.delete(id) > 0;
    }
}
