package com.levi9.code9.shoppingservice.service.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.shoppingservice.dto.request.ShoppingCartRequestDTO;
import com.levi9.code9.shoppingservice.dto.response.BookWithAuthorResponseDTO;
import com.levi9.code9.shoppingservice.dto.response.ShoppingProductResponseDTO;
import com.levi9.code9.shoppingservice.model.ShoppingCart;
import com.levi9.code9.shoppingservice.model.ShoppingItem;
import com.levi9.code9.shoppingservice.repository.ShoppingCartRepository;
import com.levi9.code9.shoppingservice.repository.ShoppingItemRepository;
import com.levi9.code9.shoppingservice.security.JwtTokenProvider;
import com.levi9.code9.shoppingservice.service.ShoppingCartService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ShoppingCartRepository _shoppingCartRepository;

	@Autowired
	private ShoppingItemRepository _shoppingItemRepository;

	@Override
	public ShoppingCart addProductsToCart(List<ShoppingCartRequestDTO> shoppingCartProducts) {
		Long userId = JwtTokenProvider.USER_CONTEXT.get().getUserId();
		ShoppingCart shoppingCart = getShoppingCartRepository().findBy_userId(userId).orElse(new ShoppingCart());
		shoppingCart.setUserId(userId);
		Set<ShoppingItem> items = new HashSet<ShoppingItem>();
		shoppingCartProducts.forEach(product -> {
			ShoppingItem item = new ShoppingItem();
			item.setProductId(product.getProductId());
			item.setQuantity(product.getQuantity());
			items.add(item);
		});
		shoppingCart.addItems(items);
		shoppingCart = getShoppingCartRepository().save(shoppingCart);
		return shoppingCart;
	}

	public void test() {
//		Long userId = JwtTokenProvider.USER_CONTEXT.get().getUserId();
//		ShoppingCart shoppingCart = getShoppingCartRepository().findBy_userId(userId).orElse(new ShoppingCart());
//		shoppingCart.setUserId(userId);
//		Set<ShoppingItem> items = new HashSet<ShoppingItem>();
//		shoppingCartProducts.forEach(product -> {
//			Long productId = product.getProductId();
//			int quantity = product.getQuantity();
//			ShoppingItem item = shoppingCart.containsProduct(productId);
//			if(item == null) {
//				item = new ShoppingItem();
//				item.setProductId(productId);
//				item.setQuantity(quantity);
//			} else {
//				item.setQuantity(item.getQuantity() + quantity);
//			}
//			
//			items.add(item);
//		});
//		shoppingCart.addItems(items);
//		getShoppingCartRepository().save(shoppingCart);
//		return null;
	}

	@Override
	public ShoppingCart getShoppingCartProducts() {
		Long userId = JwtTokenProvider.USER_CONTEXT.get().getUserId();
		return getShoppingCartRepository().findBy_userId(userId).get();
	}

	@Override
	public List<ShoppingProductResponseDTO> updateShoppingCartProducts(
			List<ShoppingCartRequestDTO> shoppingCartProducts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteShoppingCartProducts(List<Long> productsIds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void emptyShoppingCart() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ShoppingProductResponseDTO> populateShoppingCartProductsResponse(Set<ShoppingItem> items,
			List<BookWithAuthorResponseDTO> booksData) {
		Map<Long, Object> map = booksData.stream()
				.collect(Collectors.toMap(BookWithAuthorResponseDTO::getId, item -> item));
		List<ShoppingProductResponseDTO> shoppingProductData = new ArrayList<ShoppingProductResponseDTO>();
		for (ShoppingItem item : items) {
			@SuppressWarnings("unchecked")
			BookWithAuthorResponseDTO bookAuthorInfo = (BookWithAuthorResponseDTO) map.get(item.getId()); 
			shoppingProductData.add(new ShoppingProductResponseDTO(bookAuthorInfo, item.getQuantity()));
		}
		return shoppingProductData;

	}

}
