package com.levi9.code9.bookservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.levi9.code9.bookservice.client.AuthorServiceClient;
import com.levi9.code9.bookservice.dto.request.BookAuthorListRequestDTO;
import com.levi9.code9.bookservice.dto.request.BookAuthorsRequestDTO;
import com.levi9.code9.bookservice.dto.request.BookRequestDTO;
import com.levi9.code9.bookservice.dto.response.AuthorResponseDTO;
import com.levi9.code9.bookservice.dto.response.BookAuthorResponseDTO;
import com.levi9.code9.bookservice.dto.response.BookResponseDTO;
import com.levi9.code9.bookservice.dto.response.BookWithAuthorResponseDTO;
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
	private AuthorServiceClient _authorService;

	@Autowired
	private BookService _bookService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public BookWithAuthorResponseDTO createBook(@Valid @RequestBody BookRequestDTO bookDTO) {
		BookResponseDTO bookRespDTO = getBookService().createBook(bookDTO);
		log.info("Fetching authors data...");
		List<AuthorResponseDTO> authors = getAuthorService()
				.addBookAuthors(new BookAuthorsRequestDTO(bookRespDTO.getId(), bookDTO.getAuthorsIds()));

		return new BookWithAuthorResponseDTO(bookRespDTO, authors);
	}

	@GetMapping(value = "{id}")
	public BookWithAuthorResponseDTO getBook(@PathVariable("id") Long id) {
		BookResponseDTO bookRespDTO = getBookService().getBookById(id);
		log.info("Fetching authors data...");
		List<AuthorResponseDTO> authors = getAuthorService().getBookAuthors(bookRespDTO.getId());
		return new BookWithAuthorResponseDTO(bookRespDTO, authors);

	}

	@GetMapping
	public List<BookWithAuthorResponseDTO> getBooks() {
		List<BookResponseDTO> booksData = getBookService().getAllBooks();
		BookAuthorListRequestDTO listOfBooks = new BookAuthorListRequestDTO(
				booksData.stream().map(book -> book.getId()).collect(Collectors.toList()));
		log.info("Fetching authors data...");
		List<BookAuthorResponseDTO> booksAuthors = getAuthorService().getBooksAuthors(listOfBooks);
		return getBookService().fillBooksDataWithAuthorsData(booksData, booksAuthors);

	}

	@GetMapping(value = "", params = "genreId")
	public List<BookWithAuthorResponseDTO> getBooksByGenre(@RequestParam("genreId") Long genreId) {
		List<BookResponseDTO> booksData = getBookService().getBooksByGenre(genreId);
		BookAuthorListRequestDTO listOfBooks = new BookAuthorListRequestDTO(
				booksData.stream().map(book -> book.getId()).collect(Collectors.toList()));
		log.info("Fetching authors data...");
		List<BookAuthorResponseDTO> booksAuthors = getAuthorService().getBooksAuthors(listOfBooks);
		return getBookService().fillBooksDataWithAuthorsData(booksData, booksAuthors);
	}

	@GetMapping(value = "", params = "genre")
	public List<BookWithAuthorResponseDTO> getBooksByGenre(@RequestParam("genre") String genreName) {
		List<BookResponseDTO> booksData = getBookService().getBooksByGenreName(genreName);
		BookAuthorListRequestDTO listOfBooks = new BookAuthorListRequestDTO(
				booksData.stream().map(book -> book.getId()).collect(Collectors.toList()));
		log.info("Fetching authors data...");
		List<BookAuthorResponseDTO> booksAuthors = getAuthorService().getBooksAuthors(listOfBooks);
		return getBookService().fillBooksDataWithAuthorsData(booksData, booksAuthors);
	}
	
	
	@GetMapping(value = "", params = "title")
	public List<BookWithAuthorResponseDTO> getBooksByTitle(@RequestParam("title") String title) {
		List<BookResponseDTO> booksData = getBookService().getBooksByTitle(title);
		BookAuthorListRequestDTO listOfBooks = new BookAuthorListRequestDTO(
				booksData.stream().map(book -> book.getId()).collect(Collectors.toList()));
		log.info("Fetching authors data...");
		List<BookAuthorResponseDTO> booksAuthors = getAuthorService().getBooksAuthors(listOfBooks);
		return getBookService().fillBooksDataWithAuthorsData(booksData, booksAuthors);
	}

	
	
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "{id}")
	public BookWithAuthorResponseDTO updateBook(@PathVariable("id") Long id,
			@Valid @RequestBody BookRequestDTO bookDTO) {
		BookResponseDTO bookRespDTO = getBookService().updateBook(id, bookDTO);
		log.info("Updating book authors...");
		List<AuthorResponseDTO> authors = getAuthorService()
				.replaceBookAuthors(new BookAuthorsRequestDTO(bookRespDTO.getId(), bookDTO.getAuthorsIds()));
		return new BookWithAuthorResponseDTO(bookRespDTO, authors);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "{id}")
	public boolean deleteBook(@PathVariable("id") Long id) {
		boolean result = getBookService().deleteBook(id);
		log.info("Removing authors data...");
		getAuthorService().removeBookAuthors(id);
		return result;
	}

}
