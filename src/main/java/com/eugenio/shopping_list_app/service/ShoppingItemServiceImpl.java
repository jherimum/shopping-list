package com.eugenio.shopping_list_app.service;

import com.eugenio.shopping_list_app.repository.ShoppingItem;
import com.eugenio.shopping_list_app.repository.ShoppingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ShoppingItem saveOrUpdate(ShoppingItem shoppingItem) {
        return this.shoppingItemRepository.save(shoppingItem);
    }

    @Override
    public Optional<ShoppingItem> get(long id) {
        return shoppingItemRepository.findById(id);
    }

    @Override
    public void delete(long id) {
        this.shoppingItemRepository.deleteById(id);
    }

    @Override
    public Page<ShoppingItem> find(Pageable pageable) {
        return this.shoppingItemRepository.findAll(pageable);
    }


}
