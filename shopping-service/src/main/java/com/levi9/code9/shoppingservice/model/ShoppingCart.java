package com.levi9.code9.shoppingservice.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

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
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long _id;

	@Column(name = "user_id", nullable = false)
	private Long _userId;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "shopping_cart_item", joinColumns = {
			@JoinColumn(name = "shopping_cart_id") }, inverseJoinColumns = { @JoinColumn(name = "item_id") })
	@Builder.Default
	private Set<ShoppingItem> _items = new HashSet<ShoppingItem>();

}
