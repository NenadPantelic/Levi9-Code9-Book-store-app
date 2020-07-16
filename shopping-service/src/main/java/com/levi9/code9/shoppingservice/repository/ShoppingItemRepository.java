package com.levi9.code9.shoppingservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.levi9.code9.shoppingservice.model.ShoppingItem;

@Repository
public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {

	public long deleteBy_productId(Long productId);

	public ShoppingItem findBy_productId(Long productId);

	@Modifying
	@Transactional
	@Query("UPDATE ShoppingItem item SET item._productAvailable=false WHERE item._productId= :productId")
	void updateItemProductState(@Param("productId") Long productId);

	// NOTE: optionally add total number of sold books (for admin)
	@Query(value = "SELECT item.product_id FROM shopping_item item GROUP BY item.product_id ORDER BY SUM(item.quantity) DESC LIMIT :top", nativeQuery = true)
	public List<Long> findBestSellerProductsIds(@Param("top") int top);

}
