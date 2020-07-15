package com.levi9.code9.shoppingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.levi9.code9.shoppingservice.model.ShoppingItem;

@Repository
public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {

	public long deleteBy_productId(Long productId);
}
