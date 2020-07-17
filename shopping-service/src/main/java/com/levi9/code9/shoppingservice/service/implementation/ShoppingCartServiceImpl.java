package com.levi9.code9.shoppingservice.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.shoppingservice.dto.request.ShoppingCartRequestDTO;
import com.levi9.code9.shoppingservice.dto.response.BookWithAuthorResponseDTO;
import com.levi9.code9.shoppingservice.dto.response.ShoppingProductResponseDTO;
import com.levi9.code9.shoppingservice.exception.ResourceNotFoundException;
import com.levi9.code9.shoppingservice.model.ShoppingCart;
import com.levi9.code9.shoppingservice.model.ShoppingItem;
import com.levi9.code9.shoppingservice.repository.ShoppingCartRepository;
import com.levi9.code9.shoppingservice.repository.ShoppingItemRepository;
import com.levi9.code9.shoppingservice.repository.ShoppingOrderRepository;
import com.levi9.code9.shoppingservice.security.JwtTokenProvider;
import com.levi9.code9.shoppingservice.service.ShoppingCartService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ShoppingCartRepository _shoppingCartRepository;

	@Autowired
	private ShoppingOrderRepository _shoppingOrderRepository;

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
	public ShoppingCart addProductToCart(ShoppingCartRequestDTO shoppingCartProduct) {
		log.info("Adding product to the shopping cart....");
		Long userId = JwtTokenProvider.USER_CONTEXT.get().getUserId();
		ShoppingCart shoppingCart = getShoppingCartRepository().findBy_userId(userId).orElse(new ShoppingCart());
		shoppingCart.setUserId(userId);
		// item already exists - just update quantity
		ShoppingItem item = shoppingCart.getShoppingItemByProductId(shoppingCartProduct.getProductId());
		if (item != null) {
			int newQuantity = item.getQuantity() + shoppingCartProduct.getQuantity();
			item.setQuantity(newQuantity);
			getShoppingItemRepository().save(item);
		} else {
			item = new ShoppingItem();
			item.setProductId(shoppingCartProduct.getProductId());
			item.setQuantity(shoppingCartProduct.getQuantity());
			String name = shoppingCartProduct.getProductName();
			item.setProductName(name != null ? name : "unknown");
			shoppingCart.addItem(item);
		}

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
		for (ShoppingCartRequestDTO product : shoppingCartProducts) {
			Long productId = product.getProductId();
			ShoppingItem item = shoppingCart.getShoppingItemByProductId(productId);
			if (item == null) {
				throw new ResourceNotFoundException(
						"The product with the id = " + productId + " is not present in the shopping cart");
			}
			item.setQuantity(product.getQuantity());
			getShoppingItemRepository().save(item);

		}

		shoppingCart = getShoppingCartRepository().save(shoppingCart);
		return shoppingCart;
	}

	@Override
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
	public void emptyShoppingCart() {
		log.info("Emptying shopping cart");
		ShoppingCart shoppingCart = fetchShoppingCart();
		getShoppingCartRepository().delete(shoppingCart);

	}

	@Override
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
				// TODO: this should not be here - refactor this
				item.setPrice(bookAuthorInfo.getUnitPrice());
				getShoppingItemRepository().save(item);
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
		getShoppingOrderRepository().updateOrdersBuyerState(userId);

	}

	@Override
	public void deleteShoppingCartItemsByProductId(Long productId) {
		log.info("Delete shopping cart items for product with id = " + productId);
		List<ShoppingItem> items = getShoppingItemRepository().findBy_productId(productId);
		log.info("Removing shopping items....");
		for (ShoppingItem item : items) {
			ShoppingCart shoppingCart = getShoppingCartRepository().findCartByShoppingItem(item.getId());
			System.out.println(shoppingCart);
			if(shoppingCart != null) {
				shoppingCart.removeItem(item);
				getShoppingCartRepository().save(shoppingCart);
			}
			
		}

		getShoppingItemRepository().updateItemProductState(productId);

		// getShoppingItemRepository().deleteBy_productId(productId);
	}

}
