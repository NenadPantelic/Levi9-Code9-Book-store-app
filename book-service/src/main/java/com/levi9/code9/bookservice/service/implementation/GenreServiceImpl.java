package com.levi9.code9.bookservice.service.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.bookservice.dto.request.GenreRequestDTO;
import com.levi9.code9.bookservice.dto.response.GenreResponseDTO;
import com.levi9.code9.bookservice.exception.ResourceNotFoundException;
import com.levi9.code9.bookservice.mapper.GenreMapper;
import com.levi9.code9.bookservice.model.Genre;
import com.levi9.code9.bookservice.repository.GenreRepository;
import com.levi9.code9.bookservice.service.GenreService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@Service
@Transactional
public class GenreServiceImpl implements GenreService {

	@Autowired
	private GenreRepository _genreRepository;

	@Autowired
	private GenreMapper _genreMapper;

	@Override
	public GenreResponseDTO createGenre(GenreRequestDTO genreDTO) {
		log.info("Adding a new genre...");
		Genre genre = getGenreMapper().mapToEntity(genreDTO);
		getGenreRepository().save(genre);
		log.info("Genre successfully added.");
		return getGenreMapper().mapToDTO(genre);
	}

	@Override
	public List<GenreResponseDTO> getAllGenres() {
		log.info("Fetching all genres...");
		return getGenreMapper().mapToDTOList(getGenreRepository().findAll());
	}

	@Override
	public GenreResponseDTO getGenreById(Long id) {
		Genre genre = fetchGenreById(id);
		return getGenreMapper().mapToDTO(genre);
	}

	@Override
	public GenreResponseDTO updateGenre(Long id, GenreRequestDTO genreDTO) {
		Genre genre = fetchGenreById(id);
		log.info("Updating the genre with the id = " + id);
		Genre newGenre = getGenreMapper().mapToEntity(genreDTO);
		newGenre.setId(id);
		newGenre = getGenreRepository().save(newGenre);
		log.info("Genre successfully updated.");
		return getGenreMapper().mapToDTO(newGenre);
	}

	@Override
	public void deleteGenre(Long id) {
		log.info("Deleting the genre with id = " + id);
		Genre genre = fetchGenreById(id);
		getGenreRepository().delete(genre);
		log.info("Genre successfully deleted.");
	}

	@Override
	public Genre fetchGenreById(Long id) {
		log.info("Fetching the genre with id = " + id);
		Genre genre = getGenreRepository().findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("The genre with the id = " + id + " doesn't exist."));
		return genre;
	}

}
