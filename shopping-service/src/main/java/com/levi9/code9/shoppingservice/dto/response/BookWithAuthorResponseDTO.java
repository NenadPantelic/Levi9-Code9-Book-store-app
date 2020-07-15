package com.levi9.code9.shoppingservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookWithAuthorResponseDTO {
	private Long _id;
	private String _title;
	private String _description;
	private BigDecimal _unitPrice;
	private String _publisher;
	private LocalDate _releaseDate;
	private List<GenreResponseDTO> _genres;
	private Integer _quantity;
	private List<AuthorResponseDTO> _authors;



}
