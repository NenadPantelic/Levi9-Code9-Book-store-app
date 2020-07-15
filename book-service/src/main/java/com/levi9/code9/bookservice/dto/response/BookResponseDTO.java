package com.levi9.code9.bookservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
public class BookResponseDTO {

	private Long _id;
	private String _title;
	private String _description;
	private BigDecimal _unitPrice;
	private String _publisher;
	private LocalDate _releaseDate;
	private List<GenreResponseDTO> _genres;
	private Integer _quantity;
}