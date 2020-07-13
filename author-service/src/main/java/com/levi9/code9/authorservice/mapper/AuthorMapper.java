package com.levi9.code9.authorservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.levi9.code9.authorservice.dto.request.AuthorRequestDTO;
import com.levi9.code9.authorservice.dto.response.AuthorResponseDTO;
import com.levi9.code9.authorservice.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

	AuthorResponseDTO mapToDTO(Author author);

	List<AuthorResponseDTO> mapToDTOList(Iterable<Author> authors);

	List<AuthorResponseDTO> mapToDTOList(List<Author> authors);

	Author mapToEntity(AuthorRequestDTO dto);

}
