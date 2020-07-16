package com.levi9.code9.bookservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookQuantityReductionRequestDTO {

	private Long _bookId;
	private Integer _quantity;
}
