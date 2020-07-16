package com.levi9.code9.shoppingservice.service.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.shoppingservice.dto.request.ShoppingCartRequestDTO;
import com.levi9.code9.shoppingservice.dto.response.BookWithAuthorResponseDTO;
import com.levi9.code9.shoppingservice.dto.response.ShoppingProductResponseDTO;
import com.levi9.code9.shoppingservice.exception.ExistingShoppingCartException;
import com.levi9.code9.shoppingservice.exception.ResourceNotFoundException;
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
	public ShoppingCart fetchShoppingCart() {
		log.info("Fetching shopping cart for the current user");
		Long userId = JwtTokenProvider.USER_CONTEXT.get().getUserId();
		ShoppingCart shoppingCart = getShoppingCartRepository().findBy_userId(userId).orElseThrow(
				() -> new ResourceNotFoundException("The shopping cart for the current user doesn't exist"));
		return shoppingCart;

	}

	@Override
	public ShoppingCart saveShoppingCart(List<ShoppingCartRequestDTO> shoppingCartProducts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ShoppingCart addProductsToCart(List<ShoppingCartRequestDTO> shoppingCartProducts) {
		log.info("Adding products to the shopping cart....");
		Long userId = JwtTokenProvider.USER_CONTEXT.get().getUserId();
		if (getShoppingCartRepository().findBy_userId(userId).isPresent()) {
			throw new ExistingShoppingCartException("Shopping cart for this user already exists.");
		}
		ShoppingCart shoppingCart = new ShoppingCart();
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

	@Override
	public ShoppingCart getShoppingCartProducts() {
		log.info("Fetching products from the shopping cart....");
		return fetchShoppingCart();
	}

	@Override
	public ShoppingCart updateShoppingCartProducts(List<ShoppingCartRequestDTO> shoppingCartProducts) {
		log.info("Updating products in the shopping cart...");
		ShoppingCart shoppingCart = fetchShoppingCart();
		Set<ShoppingItem> items = new HashSet<ShoppingItem>();
		for (ShoppingCartRequestDTO product : shoppingCartProducts) {
			Long productId = product.getProductId();
			int quantity = product.getQuantity();
			ShoppingItem item = shoppingCart.getShoppingItemByProductId(productId);
			if (item == null) {
				item = new ShoppingItem();
				item.setProductId(productId);
				item.setQuantity(quantity);
				items.add(item);
			} else {
				item.setQuantity(quantity);
			}

		}
		shoppingCart.addItems(items);
		shoppingCart = getShoppingCartRepository().save(shoppingCart);
		return shoppingCart;
	}

	@Override
	@Transactional
	public void deleteShoppingCartProducts(List<Long> productsIds) {
		log.info("Removing products from the shopping cart.");
		ShoppingCart shoppingCart = fetchShoppingCart();
		for (Long productId : productsIds) {
			ShoppingItem item = shoppingCart.getShoppingItemByProductId(productId);
			if (item == null) {
				throw new ResourceNotFoundException(
						"The product with the id = " + productId + " is not present in the shopping cart of this user");
			} else {
				shoppingCart.removeItem(item);
			}
		}
		getShoppingCartRepository().save(shoppingCart);

	}

	@Override
	@Transactional
	public void emptyShoppingCart() {
		log.info("Emptying shopping cart");
		ShoppingCart shoppingCart = fetchShoppingCart();
		getShoppingCartRepository().delete(shoppingCart);

	}

	@Override
	@Transactional
	public List<ShoppingProductResponseDTO> populateShoppingCartProductsResponse(Set<ShoppingItem> items,
			List<BookWithAuthorResponseDTO> booksData) {
		Map<Long, Object> map = booksData.stream()
				.collect(Collectors.toMap(BookWithAuthorResponseDTO::getId, item -> item));
		List<ShoppingProductResponseDTO> shoppingProductData = new ArrayList<ShoppingProductResponseDTO>();
		log.info("Creating shopping cart response...");
		for (ShoppingItem item : items) {
			if (map.containsKey(item.getProductId())) {
				@SuppressWarnings("unchecked")
				BookWithAuthorResponseDTO bookAuthorInfo = (BookWithAuthorResponseDTO) map.get(item.getProductId());
				shoppingProductData.add(new ShoppingProductResponseDTO(bookAuthorInfo, item.getQuantity()));
			}
		}
		return shoppingProductData;

	}

	@Override
	@Transactional
	public void deleteShoppingCartByUserId(Long userId) {
		log.info("Delete shopping cart for user with id = " + userId);
		getShoppingCartRepository().deleteBy_userId(userId);

	}

	@Override
	@Transactional
	public void deleteShoppingCartItemsByProductId(Long productId) {
		log.info("Delete shopping cart items for product with id = " + productId);
		ShoppingItem item = getShoppingItemRepository().findBy_productId(productId);

		if (item != null) {
			List<ShoppingCart> shoppingCarts = getShoppingCartRepository().findCartsByProductId(productId);
			for (ShoppingCart cart : shoppingCarts) {
				log.info("Removing shopping items....");
				cart.removeItem(item);
				getShoppingCartRepository().save(cart);
			}
		}
		getShoppingItemRepository().deleteBy_productId(productId);

	}

}
