package com.levi9.code9.shoppingservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.levi9.code9.shoppingservice.model.ShoppingOrder;

@Repository
public interface ShoppingOrderRepository extends JpaRepository<ShoppingOrder, Long> {

	public List<ShoppingOrder> findBy_userId(Long userId);

	@Modifying
	@Transactional
	@Query("UPDATE ShoppingOrder o SET o._buyerActive=false WHERE o._userId= :userId")
	public void updateOrdersBuyerState(@Param("userId") Long userId);

}
