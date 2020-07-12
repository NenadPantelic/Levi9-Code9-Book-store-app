package com.levi9.code9.bookservice.service;

import java.util.List;

import com.levi9.code9.bookservice.dto.request.BookRequestDTO;
import com.levi9.code9.bookservice.dto.response.BookResponseDTO;
import com.levi9.code9.bookservice.model.Book;

public interface BookService {

	
	public BookResponseDTO createBook(BookRequestDTO bookDTO);
	public List<BookResponseDTO> getAllBooks();
	public BookResponseDTO getBookById(Long id);
	public BookResponseDTO updateBook(Long id, BookRequestDTO bookDTO);
	public boolean deleteBook(Long id);
	public Book fetchBookById(Long id);
	public List<BookResponseDTO> getBooksByIds(List<Long> ids);
	public List<BookResponseDTO> getBooksByGenre(Long genreId);
	public List<BookResponseDTO> getBooksByGenreName(String genreName);


}
