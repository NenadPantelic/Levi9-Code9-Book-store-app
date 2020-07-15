package com.levi9.code9.shoppingservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.shoppingservice.client.BookServiceClient;
import com.levi9.code9.shoppingservice.dto.request.BookListRequestDTO;
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

	@PreAuthorize("hasAuthority('BUYER')")
	@PostMapping(value = "")
	public List<ShoppingProductResponseDTO> addProductsToCart(
			@RequestBody List<ShoppingCartRequestDTO> shoppingCartProducts) {
		ShoppingCart shoppingCart = getShoppingCartService().addProductsToCart(shoppingCartProducts);
		List<ShoppingProductResponseDTO> shoppingCartContent = createShoppingCartResponse(shoppingCart);
		log.info("Shopping cart products successfully added.");
		return shoppingCartContent;

	}

	@PreAuthorize("hasAuthority('BUYER')")
	@GetMapping(value = "")
	public List<ShoppingProductResponseDTO> getShoppingCartProducts() {
		ShoppingCart shoppingCart = getShoppingCartService().getShoppingCartProducts();
		List<ShoppingProductResponseDTO> shoppingCartContent = createShoppingCartResponse(shoppingCart);
		return shoppingCartContent;
	}

	@PreAuthorize("hasAuthority('BUYER')")
	@PutMapping(value = "")
	public List<ShoppingProductResponseDTO> updateShoppingCartProducts(
			@RequestBody List<ShoppingCartRequestDTO> shoppingCartProducts) {
		ShoppingCart shoppingCart = getShoppingCartService().updateShoppingCartProducts(shoppingCartProducts);
		List<ShoppingProductResponseDTO> productsData = new ArrayList<ShoppingProductResponseDTO>();
		List<ShoppingProductResponseDTO> shoppingCartContent = createShoppingCartResponse(shoppingCart);
		log.info("Shopping cart products successfully updated.");
		return shoppingCartContent;
	}

	private List<ShoppingProductResponseDTO> createShoppingCartResponse(ShoppingCart shoppingCart) {
		List<ShoppingProductResponseDTO> productsData = new ArrayList<ShoppingProductResponseDTO>();
		log.info("Fetching book and author data from book microservice upon shopping cart update...");
		List<BookWithAuthorResponseDTO> booksData = getBookServiceClient().getBooksByListOfIds(new BookListRequestDTO(
				shoppingCart.getItems().stream().map(item -> item.getProductId()).collect(Collectors.toList())));
		productsData = getShoppingCartService().populateShoppingCartProductsResponse(shoppingCart.getItems(),
				booksData);
		return productsData;
	}

	@PreAuthorize("hasAuthority('BUYER')")
	@DeleteMapping(value = "list")
	public void deleteProductsFromShoppingCart(@RequestBody BookListRequestDTO bookList) {
		getShoppingCartService().deleteShoppingCartProducts(bookList.getBooksIds());
	}

	@PreAuthorize("hasAuthority('BUYER')")
	@DeleteMapping(value = "")
	public void emptyShoppingCart() {
		getShoppingCartService().emptyShoppingCart();
	}
	
	
	// targeted by User and Book microservice
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "", params="userId")
	public void deleteShoppingCartByUserId(@RequestParam("userId") Long userId) {
		getShoppingCartService().deleteShoppingCartByUserId(userId);
	}
	
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "", params="productId")
	public void deleteShoppingCartByProductId(@RequestParam("productId") Long productId) {
		getShoppingCartService().deleteShoppingCartItemsByProductId(productId);
	}
}
