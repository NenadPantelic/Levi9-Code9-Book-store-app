package com.levi9.code9.shoppingservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.shoppingservice.client.BookServiceClient;
import com.levi9.code9.shoppingservice.dto.request.BookListRequestDTO;
import com.levi9.code9.shoppingservice.dto.response.BookWithAuthorResponseDTO;
import com.levi9.code9.shoppingservice.dto.response.ShoppingOrderResponseDTO;
import com.levi9.code9.shoppingservice.dto.response.SoldItemStatsResponseDTO;
import com.levi9.code9.shoppingservice.service.ShoppingOrdersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/orders/")
@Api(tags="ShoppingOrderEndpoints")
public class ShoppingOrderController {

	@Autowired
	private ShoppingOrdersService _shoppingOrderService;

	@Autowired
	private BookServiceClient _bookServiceClient;

	@ApiOperation(value="Get orders made by a user")
	@PreAuthorize("hasAuthority('BUYER')")
	@GetMapping(value = "user")
	public List<ShoppingOrderResponseDTO> getOrdersByUser() {
		return getShoppingOrderService().getOrdersByUser();
	}

	@ApiOperation(value="Get all orders")
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = "")
	public List<ShoppingOrderResponseDTO> getAllOrders() {
		return getShoppingOrderService().getAllOrders();
	}

	@ApiOperation(value="Get the best seller products(books)")
	@GetMapping(value = "statistics", params = "top")
	public List<BookWithAuthorResponseDTO> getBestSellerBooks(int top) {

		List<SoldItemStatsResponseDTO> bestSellerDTOList = new ArrayList<SoldItemStatsResponseDTO>();
		List<Long> bestSellerIds = getShoppingOrderService().getBestSellerBooks(top);

		List<BookWithAuthorResponseDTO> bestSellerBooks = getBookServiceClient()
				.getBooksByListOfIds(new BookListRequestDTO(bestSellerIds));

		return bestSellerBooks;
	}

}
