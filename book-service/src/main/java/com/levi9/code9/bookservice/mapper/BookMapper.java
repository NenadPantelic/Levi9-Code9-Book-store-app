package com.levi9.code9.bookservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.levi9.code9.bookservice.dto.request.BookRequestDTO;
import com.levi9.code9.bookservice.dto.response.BookResponseDTO;
import com.levi9.code9.bookservice.model.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

	BookResponseDTO mapToDTO(Book book);

	List<BookResponseDTO> mapToDTOList(Iterable<Book> books);

	List<BookResponseDTO> mapToDTOList(List<Book> books);

	Book mapToEntity(BookRequestDTO dto);

}