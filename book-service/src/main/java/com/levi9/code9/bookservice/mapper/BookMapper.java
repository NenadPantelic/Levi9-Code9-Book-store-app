package com.levi9.code9.bookservice.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.levi9.code9.bookservice.dto.request.BookRequestDTO;
import com.levi9.code9.bookservice.dto.response.BookResponseDTO;
import com.levi9.code9.bookservice.model.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

	//@Mapping(target = "authorsIds", expression = "java(book.getAuthorsIds())")
	BookResponseDTO mapToDTO(Book book);

	List<BookResponseDTO> mapToDTOList(Iterable<Book> books);

	List<BookResponseDTO> mapToDTOList(List<Book> books);

	Book mapToEntity(BookRequestDTO dto);

}