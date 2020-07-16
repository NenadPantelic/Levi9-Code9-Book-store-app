package com.levi9.code9.shoppingservice.dto.request;

import com.levi9.code9.shoppingservice.model.ShoppingCart;
import com.levi9.code9.shoppingservice.model.ShoppingItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookQuantityReductionRequestDTO {

	private Long _bookId;
	private Integer _quantity;

	public BookQuantityReductionRequestDTO(ShoppingItem item) {
		_bookId = item.getProductId();
		_quantity = item.getQuantity();
	}

}
