package com.levi9.code9.bookservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.levi9.code9.bookservice.config.FeignConfig;
import com.levi9.code9.bookservice.dto.request.BookAuthorListRequestDTO;
import com.levi9.code9.bookservice.dto.request.BookAuthorsRequestDTO;
import com.levi9.code9.bookservice.dto.response.AuthorResponseDTO;
import com.levi9.code9.bookservice.dto.response.BookAuthorResponseDTO;

@FeignClient(name = "author-service", fallback = AuthorServiceClientFallback.class, configuration = { FeignConfig.class })
public interface AuthorServiceClient {

	@PostMapping(value = "/api/v1/authors/book-authors/")
	public List<AuthorResponseDTO> addBookAuthors(@RequestBody BookAuthorsRequestDTO bookAuthors);

	@GetMapping(value = "/api/v1/authors/book-authors/", params = "bookId")
	public List<AuthorResponseDTO> getBookAuthors(@RequestParam Long bookId);

	@PostMapping(value = "/api/v1/authors/book-authors/list")
	public List<BookAuthorResponseDTO> getBooksAuthors(@RequestBody BookAuthorListRequestDTO booksIds);

	@DeleteMapping(value = "/api/v1/authors/book-authors/")
	public void removeBookAuthors(@RequestBody BookAuthorsRequestDTO bookAuthors);

	@DeleteMapping(value = "/api/v1/authors/book-authors/", params = "bookId")
	public void removeBookAuthors(@RequestParam("bookId") Long bookId);

	@PutMapping(value = "/api/v1/authors/book-authors/")
	public List<AuthorResponseDTO> replaceBookAuthors(@RequestBody BookAuthorsRequestDTO bookAuthors);

}
