package com.levi9.code9.authorservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.authorservice.client.BookServiceClient;
import com.levi9.code9.authorservice.dto.request.AuthorRequestDTO;
import com.levi9.code9.authorservice.dto.request.BookAuthorListRequestDTO;
import com.levi9.code9.authorservice.dto.request.BookAuthorsRequestDTO;
import com.levi9.code9.authorservice.dto.response.AuthorResponseDTO;
import com.levi9.code9.authorservice.dto.response.BookAuthorResponseDTO;
import com.levi9.code9.authorservice.service.AuthorService;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Data
@RestController
@Slf4j
@RequestMapping(value = "/api/v1/authors/")
public class AuthorController {

	@Autowired
	private AuthorService _authorService;
	
	@Autowired
	private BookServiceClient _bookServiceClient; 

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public AuthorResponseDTO createAuthor(@Valid @RequestBody AuthorRequestDTO AuthorDTO) {
		return getAuthorService().createAuthor(AuthorDTO);
	}

	@GetMapping
	public List<AuthorResponseDTO> getAuthors() {
		return getAuthorService().getAllAuthors();
	}

	@GetMapping(value = "{id}")
	public AuthorResponseDTO getAuthor(@PathVariable("id") Long id) {
		return getAuthorService().getAuthorById(id);
	}

	
	@PostMapping(value = "/book-authors/list")
	public List<BookAuthorResponseDTO> getBooksAuthors(@RequestBody BookAuthorListRequestDTO booksIds) {
		return getAuthorService().getBooksAndAuthors(booksIds.getBooksIds());
	}
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "{id}")
	public AuthorResponseDTO updateAuthor(@PathVariable("id") Long id, @Valid @RequestBody AuthorRequestDTO AuthorDTO) {
		return getAuthorService().updateAuthor(id, AuthorDTO);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "{id}")
	public void deleteAuthor(@PathVariable("id") Long id) {
		getAuthorService().deleteAuthor(id);
		log.info("Request author removal from book microservice....");
		getBookServiceClient().deleteBookAuthors(id);
		log.info("Author successfully removed from book microservice");

	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(value = "book-authors/")
	public List<AuthorResponseDTO> addBookAuthors(@RequestBody BookAuthorsRequestDTO bookAuthors) {
		return  getAuthorService().addBookAuthors(bookAuthors.getAuthorsIds(), bookAuthors.getBookId());
	}

	@GetMapping(value = "book-authors/", params = "bookId")
	public List<AuthorResponseDTO> getBookAuthors(@RequestParam Long bookId) {
		return getAuthorService().getBookAuthors(bookId);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "book-authors/")
	public void removeBookAuthors(@RequestBody BookAuthorsRequestDTO bookAuthors) {
		getAuthorService().removeBookAuthors(bookAuthors.getAuthorsIds(), bookAuthors.getBookId());
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "book-authors/", params="bookId")
	public void removeBookAuthors(@RequestParam("bookId") Long bookId) {
		getAuthorService().removeAllBookAuthors(bookId);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "book-authors/")
	public List<AuthorResponseDTO> replaceBookAuthors(@RequestBody BookAuthorsRequestDTO bookAuthors) {
		return getAuthorService().replaceBookAuthors(bookAuthors.getAuthorsIds(), bookAuthors.getBookId());
	}

}
