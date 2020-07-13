package com.levi9.code9.authorservice.dto.request;

import java.util.List;


import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
public class BookAuthorsRequestDTO {
	private Long _bookId;
	private List<Long> _authorsIds;
}
