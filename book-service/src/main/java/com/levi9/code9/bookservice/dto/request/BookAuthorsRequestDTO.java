package com.levi9.code9.bookservice.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
@AllArgsConstructor
public class BookAuthorsRequestDTO {
	private Long _bookId;
	private List<Long> _authorsIds;
}
