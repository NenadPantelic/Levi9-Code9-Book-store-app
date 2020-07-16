package com.levi9.code9.shoppingservice.service;

import java.util.List;

import com.levi9.code9.shoppingservice.dto.response.ShoppingOrderResponseDTO;

public interface ShoppingOrdersService {

	public List<ShoppingOrderResponseDTO> getOrdersByUser();

	public List<ShoppingOrderResponseDTO> getAllOrders();
	
	public List<Long> getBestSellerBooks(int top);

}
