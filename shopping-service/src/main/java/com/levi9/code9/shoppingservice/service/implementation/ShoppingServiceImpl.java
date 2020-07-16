package com.levi9.code9.shoppingservice.service.implementation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.shoppingservice.dto.request.ShoppingCartRequestDTO;
import com.levi9.code9.shoppingservice.dto.response.BookWithAuthorResponseDTO;
import com.levi9.code9.shoppingservice.dto.response.ShoppingOrderResponseDTO;
import com.levi9.code9.shoppingservice.exception.InsufficientProductQuantityException;
import com.levi9.code9.shoppingservice.exception.ResourceNotFoundException;
import com.levi9.code9.shoppingservice.mapper.ShoppingItemMapper;
import com.levi9.code9.shoppingservice.mapper.ShoppingOrderMapper;
import com.levi9.code9.shoppingservice.model.ShoppingCart;
import com.levi9.code9.shoppingservice.model.ShoppingItem;
import com.levi9.code9.shoppingservice.model.ShoppingOrder;
import com.levi9.code9.shoppingservice.repository.ShoppingCartRepository;
import com.levi9.code9.shoppingservice.repository.ShoppingOrderRepository;
import com.levi9.code9.shoppingservice.security.JwtTokenProvider;
import com.levi9.code9.shoppingservice.service.ShoppingService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@Service
@Transactional
public class ShoppingServiceImpl implements ShoppingService {

	@Autowired
	private ShoppingOrderRepository _shoppingOrderRepository;

	@Autowired
	private ShoppingCartRepository _shoppingCartRepository;

	@Autowired
	private ShoppingItemMapper _shoppingItemMapper;

	@Autowired
	private ShoppingOrderMapper _shoppingOrderMapper;

	// return map withs ids of products that violate quantity restriction
	@Override
	public Map<Long, Integer> getAllowedQuantities(Map<Long, Integer> availableProductQuantitesMap,
			Map<Long, Integer> requiringQuantitiesMap) {
		Map<Long, Integer> maximumQuantity = new HashMap<Long, Integer>();
		log.info("Checking quantity requirements.");
		for (Map.Entry<Long, Integer> kvp : availableProductQuantitesMap.entrySet()) {
			int availableQuantity = kvp.getValue();
			int requiredQuantity = requiringQuantitiesMap.get(kvp.getKey());
			if (availableQuantity < requiredQuantity) {
				maximumQuantity.put(kvp.getKey(), availableQuantity);
			}
		}
		return maximumQuantity;
	}

	@Override
	public boolean validateProductQuantities(List<ShoppingCartRequestDTO> shoppingCartProducts,
			List<BookWithAuthorResponseDTO> booksData) {
		Map<Long, Integer> requiredQuantitiesMap = shoppingCartProducts.stream()
				.collect(Collectors.toMap(ShoppingCartRequestDTO::getProductId, item -> item.getQuantity()));
		Map<Long, Integer> availableQuantitiesMap = booksData.stream()
				.collect(Collectors.toMap(BookWithAuthorResponseDTO::getId, item -> item.getQuantity()));
		Map<Long, Integer> allowedQuantitesMap = getAllowedQuantities(availableQuantitiesMap, requiredQuantitiesMap);
		if (allowedQuantitesMap.isEmpty()) {
			return true;
		} else {
			throw new InsufficientProductQuantityException(createQuantityResponseMessage(allowedQuantitesMap));
		}
	}

	// util method
	private String createQuantityResponseMessage(Map<Long, Integer> allowedQuantitesMap) {
		StringBuilder strBuilder = new StringBuilder(
				"Products that violate quantity constraints with their respective available quantities:\n");
		for (Map.Entry<Long, Integer> kvp : allowedQuantitesMap.entrySet()) {
			strBuilder.append("product id = " + kvp.getKey() + ", quantiy = " + kvp.getValue());
		}
		return strBuilder.toString();
	}

	@Override
	public ShoppingCart fetchShoppingCart() {
		log.info("Fetching shopping cart for the current user");
		Long userId = JwtTokenProvider.USER_CONTEXT.get().getUserId();
		ShoppingCart shoppingCart = getShoppingCartRepository().findBy_userId(userId).orElseThrow(
				() -> new ResourceNotFoundException("The shopping cart for the current user doesn't exist"));
		return shoppingCart;

	}

	@Override
	public List<Object> confirmThePurchase() {
		ShoppingCart shoppingCart = fetchShoppingCart();
		log.info("Making a purchase order....");
		BigDecimal totalPrice = BigDecimal.ZERO;
		BigDecimal totalItemCost = BigDecimal.ZERO;
		for (ShoppingItem item : shoppingCart.getItems()) {
			totalItemCost = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
			totalPrice = totalPrice.add(totalItemCost);
			log.info("Total price of the order = " + totalPrice);
		}
		ShoppingOrder shoppingOrder = ShoppingOrder.builder().orderedItems(shoppingCart.getItems())
				.totalPrice(totalPrice).userId(shoppingCart.getUserId()).build();
		return Arrays.asList(shoppingOrder, shoppingCart);


	}

	@Override
	public ShoppingOrderResponseDTO commitOrder(ShoppingOrder shoppingOrder, ShoppingCart shoppingCart) {
		// remove shopping cart
		getShoppingCartRepository().delete(shoppingCart);
		shoppingOrder = getShoppingOrderRepository().save(shoppingOrder);
		ShoppingOrderResponseDTO shoppingOrderDTO = new ShoppingOrderResponseDTO(shoppingOrder,
				getShoppingItemMapper().mapToDTOList(shoppingOrder.getOrderedItems()));
		return shoppingOrderDTO;
	}

}
