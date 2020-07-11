package com.levi9.code9.bookservice.service.implementations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.bookservice.dto.request.BookRequestDTO;
import com.levi9.code9.bookservice.dto.response.BookResponseDTO;
import com.levi9.code9.bookservice.exception.ResourceNotFoundException;
import com.levi9.code9.bookservice.mapper.BookMapper;
import com.levi9.code9.bookservice.model.Book;
import com.levi9.code9.bookservice.model.Genre;
import com.levi9.code9.bookservice.repository.BookRepository;
import com.levi9.code9.bookservice.service.BookService;
import com.levi9.code9.bookservice.service.GenreService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository _bookRepository;

	@Autowired
	private GenreService _genreService;

	@Autowired
	private BookMapper _bookMapper;

	@Override
	public BookResponseDTO createBook(BookRequestDTO bookDTO) {
		log.info("Adding new book...");
		Book book = getBookMapper().mapToEntity(bookDTO);
		Set<Genre> genres = new HashSet<>();
		// Set<BookAuthor> authors = new HashSet<>();
		bookDTO.getGenres().forEach(genreId -> {
			genres.add(getGenreService().fetchGenreById(genreId));
		});

		book.setGenres(genres);
		book = getBookRepository().save(book);
//		bookDTO.getAuthors().forEach(authorId -> {
//			BookAuthor author = new BookAuthor();
//			author.setBook(book);
//			author.setAuthorId(authorId);
//			get
//			authors.add();
//		});
		log.info("Book successfully added.");
		return getBookMapper().mapToDTO(book);
	}

	@Override
	public List<BookResponseDTO> getAllBooks() {
		log.info("Fetching books...");
		return getBookMapper().mapToDTOList(getBookRepository().findAll());
	}

	@Override
	public BookResponseDTO getBookById(Long id) {
		Book book = fetchBookById(id);
		return getBookMapper().mapToDTO(book);
	}

	@Override
	public BookResponseDTO updateBook(Long id, BookRequestDTO bookDTO) {
		Book book = fetchBookById(id);
		log.info("Updating book with the id " + id);
		Book newBook = getBookMapper().mapToEntity(bookDTO);
		newBook.setId(id);
		Set<Genre> genres = new HashSet<>();
		bookDTO.getGenres().forEach(genreId -> {
			genres.add(getGenreService().fetchGenreById(genreId));
		});
		newBook.addGenres(genres);
		newBook = getBookRepository().save(newBook);
		return getBookMapper().mapToDTO(newBook);
	}

	@Override
	public boolean deleteBook(Long id) {
		log.info("Deleting the book with the id " + id);
		getBookRepository().deleteById(id);
		return true;
	}

	@Override
	public Book fetchBookById(Long id) {
		log.info("Fetching book with id " + id);
		Book book = getBookRepository().findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("The book with the id = " + id + " doesn't exist."));
		return book;
	}

	@Override
	public List<BookResponseDTO> getBooksByIds(List<Long> ids) {
		return getBookMapper().mapToDTOList(getBookRepository().findAllById(ids));
	}

}
