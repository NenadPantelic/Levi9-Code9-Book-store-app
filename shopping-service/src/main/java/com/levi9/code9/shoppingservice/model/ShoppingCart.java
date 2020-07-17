package com.levi9.code9.shoppingservice.model;

import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "shopping_cart_item", joinColumns = {
			@JoinColumn(name = "shopping_cart_id") }, inverseJoinColumns = { @JoinColumn(name = "item_id") })
	@Builder.Default
	private Set<ShoppingItem> _items = new HashSet<ShoppingItem>();

	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	@Builder.Default
	private Date _createdAt = new Date();

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	@Builder.Default
	private Date _updatedAt = new Date();

	public void addItem(ShoppingItem item) {
		getItems().add(item);
	}

	public void addItems(Collection<ShoppingItem> items) {
		getItems().addAll(items);
	}

	public void removeItem(ShoppingItem item) {
		if (getItems().contains(item)) {
			getItems().remove(item);
		}

	}

	public ShoppingItem getShoppingItemByProductId(Long productId) {
		for (ShoppingItem item : getItems()) {
			if (item.getProductId() == productId) {
				return item;
			}
		}
		return null;
	}

}
