package com.levi9.code9.shoppingservice.service;

import java.util.List;
import java.util.Set;

import com.levi9.code9.shoppingservice.dto.request.ShoppingCartRequestDTO;
import com.levi9.code9.shoppingservice.dto.response.BookWithAuthorResponseDTO;
import com.levi9.code9.shoppingservice.dto.response.ShoppingProductResponseDTO;
import com.levi9.code9.shoppingservice.model.ShoppingCart;
import com.levi9.code9.shoppingservice.model.ShoppingItem;

public interface ShoppingCartService {

	public ShoppingCart fetchShoppingCart();

	public ShoppingCart saveShoppingCart(List<ShoppingCartRequestDTO> shoppingCartProducts);

	public ShoppingCart addProductToCart(ShoppingCartRequestDTO shoppingCartProduct);

	public ShoppingCart getShoppingCartProducts();

	public ShoppingCart updateShoppingCartProducts(List<ShoppingCartRequestDTO> shoppingCartProducts);

	public void deleteShoppingCartProducts(List<Long> productsIds);

	public void emptyShoppingCart();

	public List<ShoppingProductResponseDTO> populateShoppingCartProductsResponse(Set<ShoppingItem> items,
			List<BookWithAuthorResponseDTO> booksData);

	public void deleteShoppingCartByUserId(Long userId);

	public void deleteShoppingCartItemsByProductId(Long productId);

}
