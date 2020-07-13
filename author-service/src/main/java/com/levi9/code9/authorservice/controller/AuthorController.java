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

import com.levi9.code9.authorservice.dto.request.AuthorRequestDTO;
import com.levi9.code9.authorservice.dto.request.BookAuthorsRequestDTO;
import com.levi9.code9.authorservice.dto.response.AuthorResponseDTO;
import com.levi9.code9.authorservice.service.AuthorService;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@RestController
@RequestMapping(value = "/api/v1/authors/")
public class AuthorController {

	@Autowired
	private AuthorService _authorService;

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

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "{id}")
	public AuthorResponseDTO updateAuthor(@PathVariable("id") Long id, @Valid @RequestBody AuthorRequestDTO AuthorDTO) {
		return getAuthorService().updateAuthor(id, AuthorDTO);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "{id}")
	public boolean deleteGenre(@PathVariable("id") Long id) {
		return getAuthorService().deleteAuthor(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(value = "book-authors/")
	public void addAuthorsToBook(@RequestBody BookAuthorsRequestDTO bookAuthors) {
		getAuthorService().addAuthorsToBook(bookAuthors.getBookId(), bookAuthors.getAuthorsIds());
	}

}
