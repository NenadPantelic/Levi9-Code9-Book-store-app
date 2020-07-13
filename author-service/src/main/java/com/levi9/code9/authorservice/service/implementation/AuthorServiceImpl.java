package com.levi9.code9.authorservice.service.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.authorservice.dto.request.AuthorRequestDTO;
import com.levi9.code9.authorservice.dto.response.AuthorResponseDTO;
import com.levi9.code9.authorservice.exception.ResourceNotFoundException;
import com.levi9.code9.authorservice.mapper.AuthorMapper;
import com.levi9.code9.authorservice.model.Author;
import com.levi9.code9.authorservice.repository.AuthorRepository;
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
	public boolean deleteAuthor(Long id) {
		log.info("Deleting the author with the id " + id);
		getAuthorRepository().deleteById(id);
		log.info("Author successfully deleted.");
		return true;
	}

	@Override
	public Author fetchAuthorById(Long id) {
		log.info("Fetching author with an id = " + id);
		Author author = getAuthorRepository().findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("The author with an id = " + id + " doesn't exist."));
		return author;
	}

	@Override
	public List<AuthorResponseDTO> getAuthorsByIds(List<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

}
