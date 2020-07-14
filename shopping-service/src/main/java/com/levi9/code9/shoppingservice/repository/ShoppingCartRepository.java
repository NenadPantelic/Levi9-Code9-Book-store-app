package com.levi9.code9.shoppingservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.levi9.code9.shoppingservice.model.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

	public List<ShoppingCart> findBy_userId(Long userId);
}
