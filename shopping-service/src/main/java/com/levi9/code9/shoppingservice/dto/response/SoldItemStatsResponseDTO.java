package com.levi9.code9.shoppingservice.dto.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.levi9.code9.shoppingservice.dto.response.ShoppingOrderResponseDTO.ShoppingOrderResponseDTOBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoldItemStatsResponseDTO {
	// TODO: use this DTO for admin 
	private BookWithAuthorResponseDTO _bookData;
	private Integer _soldQuantity;
	
}
