package com.levi9.code9.bookservice.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
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

	@NotBlank(message = "Book price must be provided.")
	private BigDecimal _unitPrice;

	@NotBlank(message = "Publisher name must be provided.")
	private String _publisher;

	private LocalDate _releaseDate;

	private List<Long> _genres;
	private List<Long> _authors;
}
