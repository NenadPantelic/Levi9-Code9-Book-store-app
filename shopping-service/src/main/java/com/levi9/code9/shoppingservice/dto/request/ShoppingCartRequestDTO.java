package com.levi9.code9.shoppingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartRequestDTO {

	private Long _productId;
	private Integer _quantity;

}
