package com.levi9.code9.shoppingservice.dto.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.levi9.code9.shoppingservice.model.ShoppingOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingOrderResponseDTO {

	private Long _id;
	private List<ShoppingItemResponseDTO> _products;
	private Date _createdAt;
	private Boolean _isBuyerActive;
	private BigDecimal _totalPrice;
	
	
	public ShoppingOrderResponseDTO(ShoppingOrder order, List<ShoppingItemResponseDTO> items) {
		_id = order.getId();
		_products = items;
		_createdAt = order.getCreatedAt();
		_isBuyerActive = order.isBuyerActive();
		_totalPrice = order.getTotalPrice();
	}

}
