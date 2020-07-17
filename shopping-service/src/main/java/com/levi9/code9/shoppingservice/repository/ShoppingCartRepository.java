package com.levi9.code9.shoppingservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.levi9.code9.shoppingservice.model.ShoppingCart;
import com.levi9.code9.shoppingservice.model.ShoppingItem;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

	public Optional<ShoppingCart> findBy_userId(Long userId);

	public Long deleteBy_userId(Long userId);

	@Query(value = "SELECT cart FROM ShoppingCart cart JOIN cart._items item WHERE item._productId = :productId")
	public List<ShoppingCart> findCartsByProductId(@Param("productId") Long productId);
	
	
	@Query(value = "SELECT cart FROM ShoppingCart cart JOIN cart._items item WHERE item._id = :itemId")
	public ShoppingCart findCartByShoppingItem(@Param("itemId") Long itemId);
	
}
