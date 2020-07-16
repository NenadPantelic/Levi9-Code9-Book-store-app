package com.levi9.code9.shoppingservice.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import com.levi9.code9.shoppingservice.dto.response.ShoppingItemResponseDTO;
import com.levi9.code9.shoppingservice.model.ShoppingItem;

@Mapper(componentModel = "spring")
public interface ShoppingItemMapper {

	public ShoppingItemResponseDTO mapToDTO(ShoppingItem shoppingItem);

	public List<ShoppingItemResponseDTO> mapToDTOList(Collection<ShoppingItem> shoppingItems);
}
