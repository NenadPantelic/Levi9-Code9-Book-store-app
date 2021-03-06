package com.levi9.code9.bookservice.controller;

import java.util.List;
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
import com.levi9.code9.bookservice.client.ShoppingServiceClient;
import com.levi9.code9.bookservice.dto.request.BookAuthorsRequestDTO;
import com.levi9.code9.bookservice.dto.request.BookIdsListRequestDTO;
import com.levi9.code9.bookservice.dto.request.BookListRequestDTO;
import com.levi9.code9.bookservice.dto.request.BookQuantityReductionRequestDTO;
import com.levi9.code9.bookservice.dto.request.BookRequestDTO;
import com.levi9.code9.bookservice.dto.response.AuthorResponseDTO;
import com.levi9.code9.bookservice.dto.response.BookAuthorResponseDTO;
import com.levi9.code9.bookservice.dto.response.BookResponseDTO;
import com.levi9.code9.bookservice.dto.response.BookWithAuthorResponseDTO;
import com.levi9.code9.bookservice.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/books/")
@Api(tags = "BookEndpoints")
public class BookController {

	@Autowired
	private AuthorServiceClient _authorServiceClient;

	@Autowired
	private BookService _bookService;

	@Autowired
	private ShoppingServiceClient _shoppingServiceClient;

	@ApiOperation(value = "Create a book")
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public BookWithAuthorResponseDTO createBook(@Valid @RequestBody BookRequestDTO bookDTO) {
		BookResponseDTO bookRespDTO = getBookService().createBook(bookDTO);
		log.info("Fetching authors from author microservice...");
		List<AuthorResponseDTO> authors = getAuthorServiceClient()
				.addBookAuthors(new BookAuthorsRequestDTO(bookRespDTO.getId(), bookDTO.getAuthorsIds()));

		return new BookWithAuthorResponseDTO(bookRespDTO, authors);
	}

	@ApiOperation(value = "Get all books")
	@GetMapping
	public List<BookWithAuthorResponseDTO> getBooks() {
		List<BookResponseDTO> booksData = getBookService().getAllBooks();
		return getMergedBookAndAuthorDTOLists(booksData);
	}

	@ApiOperation(value = "Get a specific book")
	@GetMapping(value = "{id}")
	public BookWithAuthorResponseDTO getBook(@PathVariable("id") Long id) {
		BookResponseDTO bookRespDTO = getBookService().getBookById(id);
		log.info("Fetching authors from author microservice...");
		List<AuthorResponseDTO> authors = getAuthorServiceClient().getBookAuthors(bookRespDTO.getId());
		return new BookWithAuthorResponseDTO(bookRespDTO, authors);

	}

	@ApiOperation(value = "Get all books by genre id")
	@GetMapping(value = "", params = "genreId")
	public List<BookWithAuthorResponseDTO> getBooksByGenre(@RequestParam("genreId") Long genreId) {
		List<BookResponseDTO> booksData = getBookService().getBooksByGenre(genreId);
		return getMergedBookAndAuthorDTOLists(booksData);
	}

	@ApiOperation(value = "Get all books by genre name")
	@GetMapping(value = "", params = "genre")
	public List<BookWithAuthorResponseDTO> getBooksByGenre(@RequestParam("genre") String genreName) {
		List<BookResponseDTO> booksData = getBookService().getBooksByGenreName(genreName);
		return getMergedBookAndAuthorDTOLists(booksData);

	}

	@ApiOperation(value = "Get all books by title")
	@GetMapping(value = "", params = "title")
	public List<BookWithAuthorResponseDTO> getBooksByTitle(@RequestParam("title") String title) {
		List<BookResponseDTO> booksData = getBookService().getBooksByTitle(title);
		return getMergedBookAndAuthorDTOLists(booksData);

	}

	@ApiOperation(value = "Get all books by list of ids")
	@PostMapping(value = "list")
	public List<BookWithAuthorResponseDTO> getBooksByListOfIds(@RequestBody BookIdsListRequestDTO bookListRequestDTO) {
		List<BookResponseDTO> booksData = getBookService().getBooksByIds(bookListRequestDTO.getBooksIds());
		return getMergedBookAndAuthorDTOLists(booksData);

	}

	// utility method
	private List<BookWithAuthorResponseDTO> getMergedBookAndAuthorDTOLists(List<BookResponseDTO> booksData) {
		BookListRequestDTO listOfBooks = new BookListRequestDTO(
				booksData.stream().map(book -> book.getId()).collect(Collectors.toList()));
		log.info("Fetching authors from author microservice...");
		List<BookAuthorResponseDTO> booksAuthors = getAuthorServiceClient().getBooksAuthors(listOfBooks);
		return getBookService().fillBooksDataWithAuthorsData(booksData, booksAuthors);
	}

	@ApiOperation(value = "Update a specific book")
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "{id}")
	public BookWithAuthorResponseDTO updateBook(@PathVariable("id") Long id,
			@Valid @RequestBody BookRequestDTO bookDTO) {
		BookResponseDTO bookRespDTO = getBookService().updateBook(id, bookDTO);
		log.info("Updating book authors...");
		List<AuthorResponseDTO> authors = getAuthorServiceClient()
				.replaceBookAuthors(new BookAuthorsRequestDTO(bookRespDTO.getId(), bookDTO.getAuthorsIds()));
		return new BookWithAuthorResponseDTO(bookRespDTO, authors);
	}

	@ApiOperation(value = "Delete a specific book")
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "{id}")
	public void deleteBook(@PathVariable("id") Long id) {
		getBookService().deleteBook(id);
		log.info("Request books removal from book microservice....");
		getAuthorServiceClient().removeBookAndBookAuthors(id);
		log.info("Request books removal from shopping cart in shopping microservice....");
		getShoppingServiceClient().deleteShoppingCartByProductId(id);
	}

	@ApiOperation(value = "Delete books author")
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "", params = "authorId")
	public void deleteBookAuthor(@RequestParam("authorId") Long authorId) {
		log.info("Removing author's data...");
		getBookService().deleteBookAuthor(authorId);
	}

	@ApiOperation(value = "Reduce books quantity upon shopping action")
	@PostMapping(value = "reduce")
	public void reduceBooksQuantity(@RequestBody List<BookQuantityReductionRequestDTO> booksReductionList) {
		getBookService().reduceBooksQuantity(booksReductionList);
	}

}
