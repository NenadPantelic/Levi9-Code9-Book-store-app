package com.levi9.code9.bookservice.service;

import java.util.List;

import com.levi9.code9.bookservice.dto.request.BookQuantityReductionRequestDTO;
import com.levi9.code9.bookservice.dto.request.BookRequestDTO;
import com.levi9.code9.bookservice.dto.response.BookAuthorResponseDTO;
import com.levi9.code9.bookservice.dto.response.BookResponseDTO;
import com.levi9.code9.bookservice.dto.response.BookWithAuthorResponseDTO;
import com.levi9.code9.bookservice.model.Book;

public interface BookService {

	public Book buildBook(BookRequestDTO bookDTO);

	public BookResponseDTO createBook(BookRequestDTO bookDTO);

	public List<BookResponseDTO> getAllBooks();

	public BookResponseDTO getBookById(Long id);

	public BookResponseDTO updateBook(Long id, BookRequestDTO bookDTO);

	public void deleteBook(Long id);

	public Book fetchBookById(Long id);

	public List<BookResponseDTO> getBooksByIds(List<Long> ids);

	public List<BookResponseDTO> getBooksByGenre(Long genreId);

	public List<BookResponseDTO> getBooksByGenreName(String genreName);

	public List<BookResponseDTO> getBooksByTitle(String title);
	
	public void deleteBookAuthor(Long authorId);

	public List<BookWithAuthorResponseDTO> fillBooksDataWithAuthorsData(List<BookResponseDTO> booksData,
			List<BookAuthorResponseDTO> booksAuthors);
	
	public void reduceBooksQuantity(List<BookQuantityReductionRequestDTO> booksReductionList);


}
