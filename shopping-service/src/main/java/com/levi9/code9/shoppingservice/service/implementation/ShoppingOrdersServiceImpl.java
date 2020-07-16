package com.levi9.code9.shoppingservice.service.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.shoppingservice.dto.response.BookWithAuthorResponseDTO;
import com.levi9.code9.shoppingservice.dto.response.ShoppingOrderResponseDTO;
import com.levi9.code9.shoppingservice.dto.response.SoldItemStatsResponseDTO;
import com.levi9.code9.shoppingservice.mapper.ShoppingItemMapper;
import com.levi9.code9.shoppingservice.model.ShoppingOrder;
import com.levi9.code9.shoppingservice.repository.ShoppingItemRepository;
import com.levi9.code9.shoppingservice.repository.ShoppingOrderRepository;
import com.levi9.code9.shoppingservice.security.JwtTokenProvider;
import com.levi9.code9.shoppingservice.service.ShoppingOrdersService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@Service
@Transactional
public class ShoppingOrdersServiceImpl implements ShoppingOrdersService {

	@Autowired
	private ShoppingOrderRepository _shoppingOrderRepository;

	@Autowired
	private ShoppingItemMapper _shoppingItemMapper;
	
	@Autowired
	private ShoppingItemRepository _shoppingItemRepository;

	@Override
	public List<ShoppingOrderResponseDTO> getOrdersByUser() {
		Long userId = JwtTokenProvider.USER_CONTEXT.get().getUserId();
		log.info("Fetching all orders made by user with the id = " + userId);
		List<ShoppingOrder> orders = getShoppingOrderRepository().findBy_userId(userId);
		return createShoppingOrderDTOList(orders);
	}

	@Override
	public List<ShoppingOrderResponseDTO> getAllOrders() {
		log.info("Fetching all orders.");
		List<ShoppingOrder> orders = getShoppingOrderRepository().findAll();
		return createShoppingOrderDTOList(orders);
	}
	
	
	
	
	// helper method
	private List<ShoppingOrderResponseDTO> createShoppingOrderDTOList(List<ShoppingOrder> orders) {
		List<ShoppingOrderResponseDTO> ordersDTOList = new ArrayList<ShoppingOrderResponseDTO>();
		for (ShoppingOrder order : orders) {
			ordersDTOList.add(
					new ShoppingOrderResponseDTO(order, getShoppingItemMapper().mapToDTOList(order.getOrderedItems())));
		}
		return ordersDTOList;
	}

	@Override
	public List<Long> getBestSellerBooks(int top) {
		List<Long> bestSellerData =  getShoppingItemRepository().findBestSellerProductsIds(top);
		return bestSellerData;
	}
}
