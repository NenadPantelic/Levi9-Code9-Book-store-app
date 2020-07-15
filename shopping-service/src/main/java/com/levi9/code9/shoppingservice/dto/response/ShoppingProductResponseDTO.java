package com.levi9.code9.shoppingservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingProductResponseDTO {
	private Long _productId;
	private String _title;
	private String _description;
	private BigDecimal _unitPrice;
	private String _publisher;
	private LocalDate _releaseDate;
	private List<String> _genres;
	private Integer _quantity;
	private List<String> _authors;

	public ShoppingProductResponseDTO(BookWithAuthorResponseDTO bookAuthor, Integer quantity) {
		_productId = bookAuthor.getId();
		_title = bookAuthor.getTitle();
		_description = bookAuthor.getDescription();
		_unitPrice = bookAuthor.getUnitPrice();
		_publisher = bookAuthor.getPublisher();
		_releaseDate = bookAuthor.getReleaseDate();
		_genres = bookAuthor.getGenres().stream().map(genre -> genre.getName()).collect(Collectors.toList());
		_authors = bookAuthor.getAuthors().stream().map(author -> author.getFirstName() + " " + author.getLastName())
				.collect(Collectors.toList());
		_quantity = quantity;
	}

}
