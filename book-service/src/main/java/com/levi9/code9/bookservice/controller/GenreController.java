package com.levi9.code9.bookservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.bookservice.dto.request.GenreRequestDTO;
import com.levi9.code9.bookservice.dto.response.GenreResponseDTO;
import com.levi9.code9.bookservice.service.GenreService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/genres/")
public class GenreController {

	@Autowired
	private GenreService _genreService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public GenreResponseDTO createGenre(@Valid @RequestBody GenreRequestDTO genreDTO) {
		return getGenreService().createGenre(genreDTO);
	}

	@GetMapping
	public List<GenreResponseDTO> getGenres() {
		return getGenreService().getAllGenres();
	}

	@GetMapping(value = "{id}")
	public GenreResponseDTO getGenre(@PathVariable("id") Long id) {
		return getGenreService().getGenreById(id);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "{id}")
	public GenreResponseDTO updateGenre(@Valid @PathVariable("id") Long id, @RequestBody GenreRequestDTO genreDTO) {
		return getGenreService().updateGenre(id, genreDTO);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "{id}")
	public boolean deleteGenre(@PathVariable("id") Long id) {
		return getGenreService().deleteGenre(id);
	}

}
