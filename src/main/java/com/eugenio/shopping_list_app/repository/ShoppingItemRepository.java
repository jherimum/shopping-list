package com.eugenio.shopping_list_app.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingItemRepository extends CrudRepository<ShoppingItem, Long> {

    @Modifying
    @Query("DELETE from ShoppingItem si WHERE si.id =:id")
    int delete(long id);
}
