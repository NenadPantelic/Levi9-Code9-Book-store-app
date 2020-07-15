package com.levi9.code9.shoppingservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.shoppingservice.client.BookServiceClient;
import com.levi9.code9.shoppingservice.dto.request.ShoppingCartRequestDTO;
import com.levi9.code9.shoppingservice.dto.response.BookWithAuthorResponseDTO;
import com.levi9.code9.shoppingservice.dto.response.ShoppingProductResponseDTO;
import com.levi9.code9.shoppingservice.model.ShoppingCart;
import com.levi9.code9.shoppingservice.service.ShoppingCartService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/shopping-cart/")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService _shoppingCartService;

	@Autowired
	private BookServiceClient _bookServiceClient;

	@PostMapping(value = "")
	public List<ShoppingProductResponseDTO> addProductsToCart(
			@RequestBody List<ShoppingCartRequestDTO> shoppingCartProducts) {
		log.info("Adding products to shopping cart.");
		ShoppingCart cart = getShoppingCartService().addProductsToCart(shoppingCartProducts);
		List<BookWithAuthorResponseDTO> booksData = getBookServiceClient().getBooksByListOfIds(
				cart.getItems().stream().map(item -> item.getProductId()).collect(Collectors.toList()));
		return getShoppingCartService().populateShoppingCartProductsResponse(cart.getItems(), booksData);
	}

	@GetMapping(value = "")
	public List<ShoppingProductResponseDTO> getShoppingCartProducts() {
		ShoppingCart shoppingCart = getShoppingCartService().getShoppingCartProducts();
		List<ShoppingProductResponseDTO> productsData = new ArrayList<ShoppingProductResponseDTO>();
		if (shoppingCart != null) {
			List<BookWithAuthorResponseDTO> booksData = getBookServiceClient().getBooksByListOfIds(
					shoppingCart.getItems().stream().map(item -> item.getProductId()).collect(Collectors.toList()));
			productsData = getShoppingCartService().populateShoppingCartProductsResponse(shoppingCart.getItems(),
					booksData);
		}
		return productsData;
	}
}
