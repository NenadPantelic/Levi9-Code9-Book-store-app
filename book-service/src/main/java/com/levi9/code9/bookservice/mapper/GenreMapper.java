package com.levi9.code9.bookservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import com.levi9.code9.bookservice.dto.response.GenreResponseDTO;
import com.levi9.code9.bookservice.model.Genre;

@Mapper(componentModel = "spring")
public interface GenreMapper {

	GenreResponseDTO mapGenreToGenreDTO(Genre genre);

	List<GenreResponseDTO> mapGenreListToGenreDTOList(List<Genre> genres);

	Genre mapGenreDTOToGenre(GenreResponseDTO dto);

}