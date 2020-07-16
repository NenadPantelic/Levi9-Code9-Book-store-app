package com.levi9.code9.bookservice.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.levi9.code9.bookservice.dto.request.BookListRequestDTO;
import com.levi9.code9.bookservice.dto.request.BookAuthorsRequestDTO;
import com.levi9.code9.bookservice.dto.response.AuthorResponseDTO;
import com.levi9.code9.bookservice.dto.response.BookAuthorResponseDTO;


@Component
public class AuthorServiceClientFallback implements AuthorServiceClient {

	private static final String UNKNOWN_MESSAGE = "Unknown";

	@Override
	public List<AuthorResponseDTO> addBookAuthors(BookAuthorsRequestDTO bookAuthors) {
		return Arrays.asList(new AuthorResponseDTO(null, UNKNOWN_MESSAGE, UNKNOWN_MESSAGE, UNKNOWN_MESSAGE));
	}

	@Override
	public List<AuthorResponseDTO> getBookAuthors(Long bookId) {
		return Arrays.asList(new AuthorResponseDTO(null, UNKNOWN_MESSAGE, UNKNOWN_MESSAGE, UNKNOWN_MESSAGE));
	}

	@Override
	public List<BookAuthorResponseDTO> getBooksAuthors(BookListRequestDTO booksIds) {
		return Arrays.asList(new BookAuthorResponseDTO(null,
				Arrays.asList(new AuthorResponseDTO(null, UNKNOWN_MESSAGE, UNKNOWN_MESSAGE, UNKNOWN_MESSAGE))));
	}

	@Override
	public void removeBookAuthors(BookAuthorsRequestDTO bookAuthors) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBookAndBookAuthors(Long bookId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<AuthorResponseDTO> replaceBookAuthors(BookAuthorsRequestDTO bookAuthors) {
		return Arrays.asList(new AuthorResponseDTO(null, UNKNOWN_MESSAGE, UNKNOWN_MESSAGE, UNKNOWN_MESSAGE));
	}
	
}