package com.levi9.code9.shoppingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.levi9.code9.shoppingservice.model.ShoppingOrder;

@Repository
public interface OrderRepository extends JpaRepository<ShoppingOrder, Long> {

}
