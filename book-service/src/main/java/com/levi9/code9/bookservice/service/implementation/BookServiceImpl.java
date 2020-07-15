package com.levi9.code9.bookservice.service.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.bookservice.dto.request.BookListRequestDTO;
import com.levi9.code9.bookservice.dto.request.BookRequestDTO;
import com.levi9.code9.bookservice.dto.response.AuthorResponseDTO;
import com.levi9.code9.bookservice.dto.response.BookAuthorResponseDTO;
import com.levi9.code9.bookservice.dto.response.BookResponseDTO;
import com.levi9.code9.bookservice.dto.response.BookWithAuthorResponseDTO;
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
			AuthorEntity bookAuthor = getBookAuthorRepository().findById(authorId).orElse(new AuthorEntity(authorId));
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
		log.info("Fetching books by genre = " + genreName + ".");
		return getBookMapper().mapToDTOList(getBookRepository().findBooksByGenreName(genreName));
	}

	@Override
	public List<BookResponseDTO> getBooksByTitle(String title) {
		log.info("Fetching books by title = " + title + ".");
		return getBookMapper().mapToDTOList(getBookRepository().findBooksByTitle(title));

	}
	
	@Override
	public List<BookResponseDTO> getBooksByIds(List<Long> ids) {
		return getBookMapper().mapToDTOList(getBookRepository().findAllById(ids));
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
	public void deleteBook(Long id) {
		log.info("Deleting the book with an id = " + id);
		getBookRepository().softDelete(id);
		log.info("Book successfully deleted.");
	}

	@Override
	public Book fetchBookById(Long id) {
		log.info("Fetching book with an id = " + id);
		Book book = getBookRepository().findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("The book with the id = " + id + " doesn't exist."));
		return book;
	}


	@Override
	public void deleteBookAuthors(Long authorId) {
		List<Book> booksByTargetAuthor = getBookRepository().findBooksByAuthorId(authorId);
		AuthorEntity author = getBookAuthorRepository().findById(authorId).orElseThrow(
				() -> new ResourceNotFoundException("The author with the id = " + authorId + " doesn't exist."));
		booksByTargetAuthor.forEach(book -> {
			book.removeAuthor(author);
			getBookRepository().save(book);
		});
		getBookAuthorRepository().deleteById(authorId);

	}

	@Override
	public List<BookWithAuthorResponseDTO> fillBooksDataWithAuthorsData(List<BookResponseDTO> booksData,
			List<BookAuthorResponseDTO> booksAuthors) {
		Map<Long, Object> map = booksAuthors.stream()
				.collect(Collectors.toMap(BookAuthorResponseDTO::getBookId, item -> item.getAuthors()));
		List<BookWithAuthorResponseDTO> bookAuthorData = new ArrayList<BookWithAuthorResponseDTO>();
		for (BookResponseDTO bookRespDTO : booksData) {
			@SuppressWarnings("unchecked")
			List<AuthorResponseDTO> authors = (List<AuthorResponseDTO>) map.get(bookRespDTO.getId());
			bookAuthorData.add(new BookWithAuthorResponseDTO(bookRespDTO, authors));
		}
		return bookAuthorData;
	}

}
