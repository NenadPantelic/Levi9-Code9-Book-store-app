package com.levi9.code9.bookservice.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorListRequestDTO {

	private List<Long> _booksIds;
}
