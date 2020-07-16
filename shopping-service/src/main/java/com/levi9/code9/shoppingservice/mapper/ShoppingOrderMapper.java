package com.levi9.code9.shoppingservice.mapper;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import com.levi9.code9.shoppingservice.dto.response.ShoppingOrderResponseDTO;
import com.levi9.code9.shoppingservice.model.ShoppingOrder;

@Mapper(componentModel = "spring")

public interface ShoppingOrderMapper {
	public ShoppingOrderResponseDTO mapToDTO(ShoppingOrder shoppingOrder);

	public List<ShoppingOrderResponseDTO> mapToDTOList(Collection<ShoppingOrder> shoppingOrders);
}
