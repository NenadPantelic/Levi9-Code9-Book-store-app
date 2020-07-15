package com.levi9.code9.shoppingservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.levi9.code9.shoppingservice.model.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

	public Optional<ShoppingCart> findBy_userId(Long userId);

	public Long deleteBy_userId(Long userId);
}
