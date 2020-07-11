package com.levi9.code9.bookservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.levi9.code9.bookservice.dto.request.GenreRequestDTO;
import com.levi9.code9.bookservice.dto.response.GenreResponseDTO;
import com.levi9.code9.bookservice.model.Genre;

@Mapper(componentModel = "spring")
public interface GenreMapper {

	GenreResponseDTO mapToDTO(Genre genre);

	List<GenreResponseDTO> mapToDTOList(Iterable<Genre> genres);

	List<GenreResponseDTO> mapToDTOList(List<Genre> genres);

	Genre mapToEntity(GenreRequestDTO dto);

}