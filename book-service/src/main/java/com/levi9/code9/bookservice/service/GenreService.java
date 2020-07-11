package com.levi9.code9.bookservice.service;

import java.util.List;

import com.levi9.code9.bookservice.dto.request.GenreRequestDTO;
import com.levi9.code9.bookservice.dto.response.GenreResponseDTO;
import com.levi9.code9.bookservice.model.Genre;

public interface GenreService {
	
	public GenreResponseDTO createGenre(GenreRequestDTO genreDTO);
	public List<GenreResponseDTO> getAllGenres();
	public GenreResponseDTO getGenreById(Long id);
	public GenreResponseDTO updateGenre(Long id, GenreRequestDTO genreDTO);
	public boolean deleteGenre(Long id);
	public Genre fetchGenreById(Long id);

	

}
