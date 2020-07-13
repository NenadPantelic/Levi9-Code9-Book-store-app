package com.levi9.code9.bookservice.service.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.bookservice.dto.request.BookRequestDTO;
import com.levi9.code9.bookservice.dto.response.BookResponseDTO;
import com.levi9.code9.bookservice.exception.ResourceNotFoundException;
import com.levi9.code9.bookservice.mapper.BookMapper;
import com.levi9.code9.bookservice.model.Book;
import com.levi9.code9.bookservice.model.AuthorEntity;
import com.levi9.code9.bookservice.model.Genre;
import com.levi9.code9.bookservice.repository.BookAuthorRepository;
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
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository _bookRepository;

	@Autowired
	private BookAuthorRepository _bookAuthorRepository;

	@Autowired
	private GenreService _genreService;

	@Autowired
	private BookMapper _bookMapper;

	@Override
	public Book buildBook(BookRequestDTO bookDTO) {
		log.info("Adapting the book...");
		Book book = getBookMapper().mapToEntity(bookDTO);
		Set<Genre> genres = new HashSet<>();
		List<AuthorEntity> authors = new ArrayList<>();
		bookDTO.getGenresIds().forEach(genreId -> {
			genres.add(getGenreService().fetchGenreById(genreId));
		});
		book.setGenres(genres);
		for (Long authorId : bookDTO.getAuthorsIds()) {
			AuthorEntity bookAuthor = getBookAuthorRepository().findById(authorId)
					.orElse(new AuthorEntity(authorId));
			authors.add(bookAuthor);
		}

		book.setAuthors(authors);
		return book;
	}

	@Override
	public BookResponseDTO createBook(BookRequestDTO bookDTO) {
		log.info("Adding a new book...");
		Book book = getBookRepository().save(buildBook(bookDTO));
		log.info("Book successfully added.");
		return getBookMapper().mapToDTO(book);
	}

	@Override
	public List<BookResponseDTO> getAllBooks() {
		log.info("Fetching all books...");
		return getBookMapper().mapToDTOList(getBookRepository().findAll());
	}

	@Override
	public BookResponseDTO getBookById(Long id) {
		Book book = fetchBookById(id);
		return getBookMapper().mapToDTO(book);
	}

	@Override
	public List<BookResponseDTO> getBooksByGenre(Long genreId) {
		log.info("Fetching books by genre id = " + genreId + ".");
		return getBookMapper().mapToDTOList(getBookRepository().findBooksByGenre(genreId));
	}

	@Override
	public List<BookResponseDTO> getBooksByGenreName(String genreName) {
		log.info("Fetcing books by genre = " + genreName + ".");
		return getBookMapper().mapToDTOList(getBookRepository().findBooksByGenreName(genreName));
	}

	@Override
	public BookResponseDTO updateBook(Long id, BookRequestDTO bookDTO) {
		Book book = fetchBookById(id);
		log.info("Updating the book with an id = " + id);
		book = buildBook(bookDTO);
		book.setId(id);
		book = getBookRepository().save(book);
		log.info("Book successfully updated.");
		return getBookMapper().mapToDTO(book);
	}

	@Override
	public boolean deleteBook(Long id) {
		log.info("Deleting the book with an id = " + id);
		getBookRepository().softDelete(id);
		log.info("Book successfully deleted.");
		return true;
	}

	@Override
	public Book fetchBookById(Long id) {
		log.info("Fetching book with an id = " + id);
		Book book = getBookRepository().findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("The book with the id = " + id + " doesn't exist."));
		return book;
	}

	@Override
	public List<BookResponseDTO> getBooksByIds(List<Long> ids) {
		return getBookMapper().mapToDTOList(getBookRepository().findAllById(ids));
	}

}
