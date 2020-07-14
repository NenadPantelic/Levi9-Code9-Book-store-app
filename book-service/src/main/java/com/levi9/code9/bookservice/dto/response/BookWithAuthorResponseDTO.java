package com.levi9.code9.bookservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@AllArgsConstructor
public class BookWithAuthorResponseDTO {
	private Long _id;
	private String _title;
	private String _description;
	private BigDecimal _unitPrice;
	private String _publisher;
	private LocalDate _releaseDate;
	private List<GenreResponseDTO> _genres;
	// private Integer _quantity;
	private List<AuthorResponseDTO> _authors;

	public BookWithAuthorResponseDTO(BookResponseDTO book, List<AuthorResponseDTO> authors) {
		_id = book.getId();
		_title = book.getTitle();
		_description = book.getDescription();
		_unitPrice = book.getUnitPrice();
		_publisher = book.getPublisher();
		_releaseDate = book.getReleaseDate();
		_genres = book.getGenres();
		_authors = authors;
	}

}
