package com.levi9.code9.shoppingservice.dto.response;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingItemResponseDTO {

	private Long _productId;
	private Integer _quantity;
	private BigDecimal _price;
	private Boolean _productAvailable;

}
