package com.levi9.code9.bookservice.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.bookservice.dto.request.BookRequestDTO;
import com.levi9.code9.bookservice.dto.response.BookResponseDTO;
import com.levi9.code9.bookservice.service.BookService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/books/")
public class BookController {
	@Autowired
	private BookService _bookService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public BookResponseDTO createBook(@Valid @RequestBody BookRequestDTO bookDTO) {
		return getBookService().createBook(bookDTO);
	}

	@GetMapping
	public List<BookResponseDTO> getBooks() {
		return getBookService().getAllBooks();
	}

	@GetMapping(value = "{id}")
	public BookResponseDTO getBook(@PathVariable("id") Long id) {
		return getBookService().getBookById(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "{id}")
	public BookResponseDTO updateBook(@PathVariable("id") Long id, @Valid @RequestBody BookRequestDTO bookDTO) {
		return getBookService().updateBook(id, bookDTO);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "{id}")
	public boolean deleteGenre(@PathVariable("id") Long id) {
		return getBookService().deleteBook(id);
	}
}
