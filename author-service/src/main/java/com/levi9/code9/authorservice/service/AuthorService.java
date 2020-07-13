package com.levi9.code9.authorservice.service;

import java.util.List;

import com.levi9.code9.authorservice.dto.request.AuthorRequestDTO;
import com.levi9.code9.authorservice.dto.response.AuthorResponseDTO;
import com.levi9.code9.authorservice.model.Author;

public interface AuthorService {

	public Author buildAuthor(AuthorRequestDTO authorDTO);

	public AuthorResponseDTO createAuthor(AuthorRequestDTO authorDTO);

	public List<AuthorResponseDTO> getAllAuthors();

	public AuthorResponseDTO getAuthorById(Long id);

	public AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO authorDTO);

	public boolean deleteAuthor(Long id);

	public Author fetchAuthorById(Long id);

	public List<AuthorResponseDTO> getAuthorsByIds(List<Long> ids);

	public void addAuthorToBook(Author author, Long bookId);

	public void addAuthorsToBook(Long bookId, List<Long> authorsIds);

}
