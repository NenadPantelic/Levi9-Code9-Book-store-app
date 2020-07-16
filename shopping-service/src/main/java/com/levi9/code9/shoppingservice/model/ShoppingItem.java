package com.levi9.code9.shoppingservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import com.levi9.code9.shoppingservice.model.ShoppingCart.ShoppingCartBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShoppingItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long _id;

	@Column(name = "product_id", nullable = false)
	private Long _productId; // more flexible name, in case we add some other shopping items - computers e.g.

	// only for order report - some products will become inactive (deleted), so we won't be able to fetch their data,
	// and basic report should have at least product id and name
	@Column(name="product_name")
	@Builder.Default
	private String _productName = "unknown";

	@Column(name = "quantity", nullable = false)
	@Min(value = 1)
	private Integer _quantity;

	@Column(name = "is_product_available", nullable = false)
	@Builder.Default
	private Boolean _isProductAvailable = true;

}
