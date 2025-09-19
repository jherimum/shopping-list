package com.eugenio.shopping_list_app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingItemRepository extends CrudRepository<ShoppingItem, Long>, PagingAndSortingRepository<ShoppingItem, Long> {


}
