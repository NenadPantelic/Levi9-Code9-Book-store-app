package com.levi9.code9.bookservice.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
public class BookRequestDTO {

	@NotBlank(message = "Book name must be provided.")
	@Size(max = 100, message = "Length of the book name must be between 1 and 100.")
	private String _title;

	@NotBlank(message = "Description cannot be blank. This is required field.")
	private String _description;

	@NotNull(message="Book price must be provided.")
	@Min(value=1)
	private BigDecimal _unitPrice;

	@NotBlank(message = "Publisher name must be provided.")
	private String _publisher;
	
	@NotNull(message="Book quantity must be provided.")
	@Min(value=1)
	private Integer _quantity;
	
	@NotNull(message = "Release date name must be provided.")
	private LocalDate _releaseDate;

	private List<Long> _genresIds;
	private List<Long> _authorsIds;
}
