package com.levi9.code9.authorservice.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.authorservice.dto.request.AuthorRequestDTO;
import com.levi9.code9.authorservice.dto.response.AuthorResponseDTO;
import com.levi9.code9.authorservice.dto.response.BookAuthorResponseDTO;
import com.levi9.code9.authorservice.exception.ResourceNotFoundException;
import com.levi9.code9.authorservice.mapper.AuthorMapper;
import com.levi9.code9.authorservice.model.Author;
import com.levi9.code9.authorservice.model.Book;
import com.levi9.code9.authorservice.repository.AuthorRepository;
import com.levi9.code9.authorservice.repository.BookRepository;
import com.levi9.code9.authorservice.service.AuthorService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorRepository _authorRepository;

	@Autowired
	BookRepository _bookRepository;

	@Autowired
	private AuthorMapper _authorMapper;

	@Override
	public Author buildAuthor(AuthorRequestDTO authorDTO) {
		log.info("Adapting the author...");
		Author author = getAuthorMapper().mapToEntity(authorDTO);
		return author;
	}

	@Override
	public AuthorResponseDTO createAuthor(AuthorRequestDTO authorDTO) {
		log.info("Adding a new author...");
		Author author = getAuthorRepository().save(buildAuthor(authorDTO));
		log.info("Author successfully added.");
		return getAuthorMapper().mapToDTO(author);
	}

	@Override
	public List<AuthorResponseDTO> getAllAuthors() {
		log.info("Fetching all authors...");
		return getAuthorMapper().mapToDTOList(getAuthorRepository().findAll());
	}

	@Override
	public AuthorResponseDTO getAuthorById(Long id) {
		Author author = fetchAuthorById(id);
		return getAuthorMapper().mapToDTO(author);
	}

	@Override
	public AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO authorDTO) {
		Author author = fetchAuthorById(id);
		log.info("Updating the author with an id " + id);
		author = buildAuthor(authorDTO);
		author.setId(id);
		author = getAuthorRepository().save(author);
		log.info("Author successfully updated.");
		return getAuthorMapper().mapToDTO(author);
	}

	@Override
	public void deleteAuthor(Long id) {
		log.info("Deleting the author with the id " + id);
		Author author = fetchAuthorById(id);
		getAuthorRepository().delete(author);
		log.info("Author successfully deleted.");
	}

	@Override
	public Author fetchAuthorById(Long id) {
		log.info("Fetching the author with an id = " + id);
		Author author = getAuthorRepository().findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("The author with an id = " + id + " doesn't exist."));
		return author;
	}

	@Override
	public List<AuthorResponseDTO> getAuthorsByIds(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthorResponseDTO addBookAuthor(Long authorId, Long bookId) {
		log.info("Adding the author with an id = " + authorId + " to book with an id = " + bookId);
		Author author = fetchAuthorById(authorId);
		Book book = getBookRepository().findById(bookId).orElse(new Book(bookId));
		author.addBook(book);
		getAuthorRepository().save(author);
		return getAuthorMapper().mapToDTO(author);

	}

	@Override
	public List<AuthorResponseDTO> addBookAuthors(List<Long> authorsIds, Long bookId) {
		List<AuthorResponseDTO> authors = new ArrayList<AuthorResponseDTO>();
		authorsIds.forEach(authorId -> {
			authors.add(addBookAuthor(authorId, bookId));
		});
		return authors;

	}

	@Override
	public void removeBookAuthor(Long authorId, Long bookId) {
		Book book = getBookRepository().findById(bookId).orElseThrow(
				() -> new ResourceNotFoundException("The book with the given id = " + bookId + "doesn't exist"));
		Author author = fetchAuthorById(authorId);
		author.removeBook(book);
		getAuthorRepository().save(author);

	}

	@Override
	public List<AuthorResponseDTO> getBookAuthors(Long bookId) {
		List<Author> authors = getAuthorRepository().findAuthorsByBook(bookId);
		return getAuthorMapper().mapToDTOList(authors);
	}

	@Override
	public void removeBookAuthors(List<Long> authorsIds, Long bookId) {
		for (Long authorId : authorsIds) {
			removeBookAuthor(authorId, bookId);
		}

	}

	// use when book is deleted
	@Override
	public void removeAllBookAuthors(Long bookId) {
		Book book = getBookRepository().findById(bookId).orElseThrow(
				() -> new ResourceNotFoundException("The book with the given id = " + bookId + "doesn't exist"));
		List<Long> authorsIds = getAuthorRepository().findAuthorsIdsByBook(bookId);
		for (Long authorId : authorsIds) {
			removeBookAuthor(authorId, bookId);
		}
		getBookRepository().delete(book);
	}

	@Override
	public List<AuthorResponseDTO> replaceBookAuthors(List<Long> authorsIds, Long bookId) {
		Book book = getBookRepository().findById(bookId).orElseThrow(
				() -> new ResourceNotFoundException("Book with the given id = " + bookId + " doesn't exist."));
		List<Long> currentAuthors = getAuthorRepository().findAuthorsIdsByBook(bookId);
		List<AuthorResponseDTO> authors = new ArrayList<AuthorResponseDTO>();
		// remove authors that are not present anymore
		for (Long authorId : currentAuthors) {
			if (!authorsIds.contains(authorId)) {
				removeBookAuthor(authorId, bookId);
			}
		}
		// add new authors
		for (Long authorId : authorsIds) {
			if (!currentAuthors.contains(authorId)) {
				addBookAuthor(authorId, bookId);
			}
		}

		return getBookAuthors(bookId);
	}

	@Override
	public List<BookAuthorResponseDTO> getBooksAndAuthors(List<Long> booksIds) {
		List<BookAuthorResponseDTO> booksAndTheirAuthors = new ArrayList<BookAuthorResponseDTO>();
		booksIds.forEach(bookId -> {
			List<Author> authors = getAuthorRepository().findAuthorsByBook(bookId);
			booksAndTheirAuthors.add(new BookAuthorResponseDTO(bookId, getAuthorMapper().mapToDTOList(authors)));
		});
		return booksAndTheirAuthors;
	}
	
}
