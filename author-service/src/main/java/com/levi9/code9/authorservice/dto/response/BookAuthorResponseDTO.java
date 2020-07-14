package com.levi9.code9.authorservice.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorResponseDTO {

	private Long _bookId;
	private List<AuthorResponseDTO> _authors;
}
