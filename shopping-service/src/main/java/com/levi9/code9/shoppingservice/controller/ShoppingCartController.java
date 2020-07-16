package com.levi9.code9.shoppingservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.levi9.code9.shoppingservice.dto.response.ShoppingOrderResponseDTO;
import com.levi9.code9.shoppingservice.dto.response.ShoppingProductResponseDTO;
import com.levi9.code9.shoppingservice.model.ShoppingCart;
import com.levi9.code9.shoppingservice.model.ShoppingOrder;
import com.levi9.code9.shoppingservice.service.ShoppingCartService;
import com.levi9.code9.shoppingservice.service.ShoppingService;
import com.levi9.code9.shoppingservice.utils.Utils;

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
	private ShoppingService _shoppingService;

	@Autowired
	private BookServiceClient _bookServiceClient;

	@PreAuthorize("hasAuthority('BUYER')")
	@PostMapping(value = "")
	public List<ShoppingProductResponseDTO> addProductsToCart(@RequestBody ShoppingCartRequestDTO shoppingCartProduct) {
		// TODO: recode this -> use helper vars -> too complicated to read
		log.info("Fetching book and author data from book microservice upon shopping cart update...");
		List<BookWithAuthorResponseDTO> booksData = getBookServiceClient()
				.getBooksByListOfIds(new BookListRequestDTO(Arrays.asList(shoppingCartProduct.getProductId())));
		getShoppingService().validateProductQuantities(Arrays.asList(shoppingCartProduct), booksData);
		ShoppingCart shoppingCart = getShoppingCartService().addProductToCart(shoppingCartProduct);
		List<ShoppingProductResponseDTO> shoppingCartContent = createShoppingCartResponse(shoppingCart, booksData);
		log.info("Shopping cart products successfully added.");
		return shoppingCartContent;

	}

	@PreAuthorize("hasAuthority('BUYER')")
	@GetMapping(value = "")
	public List<ShoppingProductResponseDTO> getShoppingCartProducts() {
		log.info("Fetching book and author data from book microservice upon shopping cart update...");
		// TODO: think about adding validateProductQuantities here too
		ShoppingCart shoppingCart = getShoppingCartService().getShoppingCartProducts();
		List<ShoppingProductResponseDTO> shoppingCartContent = createShoppingCartResponse(shoppingCart);
		return shoppingCartContent;
	}

	@PreAuthorize("hasAuthority('BUYER')")
	@PutMapping(value = "")
	public List<ShoppingProductResponseDTO> updateShoppingCartProducts(
			@RequestBody List<ShoppingCartRequestDTO> shoppingCartProducts) {
		// TODO: recode this
		log.info("Fetching book and author data from book microservice upon shopping cart update...");
		List<BookWithAuthorResponseDTO> booksData = getBookServiceClient().getBooksByListOfIds(new BookListRequestDTO(
				shoppingCartProducts.stream().map(product -> product.getProductId()).collect(Collectors.toList())));

		getShoppingService().validateProductQuantities(shoppingCartProducts, booksData);
		ShoppingCart shoppingCart = getShoppingCartService().updateShoppingCartProducts(shoppingCartProducts);
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

	private List<ShoppingProductResponseDTO> createShoppingCartResponse(ShoppingCart shoppingCart,
			List<BookWithAuthorResponseDTO> booksData) {
		List<ShoppingProductResponseDTO> productsData = new ArrayList<ShoppingProductResponseDTO>();
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

	// for shopping cart confirmation
	@PreAuthorize("hasAuthority('BUYER')")
	@PostMapping(value = "confirm")
	public ShoppingOrderResponseDTO confirmThePurchase() {
		List<Object> shoppingObjects = getShoppingService().confirmThePurchase();
		ShoppingOrder shoppingOrder = (ShoppingOrder) shoppingObjects.get(0);
		ShoppingCart shoppingCart = (ShoppingCart) shoppingObjects.get(1);
		getBookServiceClient().reduceBooksQuantity(Utils.getQuantityReductionListFromCart(shoppingCart.getItems()));
		return getShoppingService().commitOrder(shoppingOrder, shoppingCart);
	}

	// NOTE: targeted by User and Book microservice
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "", params = "userId")
	public void deleteShoppingCartByUserId(@RequestParam("userId") Long userId) {
		getShoppingCartService().deleteShoppingCartByUserId(userId);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "", params = "productId")
	public void deleteShoppingCartByProductId(@RequestParam("productId") Long productId) {
		getShoppingCartService().deleteShoppingCartItemsByProductId(productId);
	}

}
