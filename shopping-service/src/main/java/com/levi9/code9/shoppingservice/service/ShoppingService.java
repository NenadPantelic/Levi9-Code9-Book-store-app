package com.levi9.code9.shoppingservice.service;

import java.util.List;
import java.util.Map;

import com.levi9.code9.shoppingservice.dto.request.ShoppingCartRequestDTO;
import com.levi9.code9.shoppingservice.dto.response.BookWithAuthorResponseDTO;
import com.levi9.code9.shoppingservice.dto.response.ShoppingOrderResponseDTO;
import com.levi9.code9.shoppingservice.model.ShoppingCart;
import com.levi9.code9.shoppingservice.model.ShoppingOrder;

public interface ShoppingService {
	
	public Map<Long, Integer> getAllowedQuantities(Map<Long, Integer> productsQuantityMap, Map<Long, Integer> shoppingQuantityMap);
	public boolean validateProductQuantities(List<ShoppingCartRequestDTO> shoppingCartProducts, List<BookWithAuthorResponseDTO> booksData);
	//public ShoppingOrderResponseDTO confirmThePurchase(ShoppingCart shoppingCart);
	public ShoppingCart fetchShoppingCart();
	public List<Object> confirmThePurchase();
	public ShoppingOrderResponseDTO commitOrder(ShoppingOrder shoppingOrder, ShoppingCart shoppingCart);
	

	
}
