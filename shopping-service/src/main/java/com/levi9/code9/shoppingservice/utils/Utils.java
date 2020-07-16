package com.levi9.code9.shoppingservice.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.levi9.code9.shoppingservice.dto.request.BookQuantityReductionRequestDTO;
import com.levi9.code9.shoppingservice.dto.response.ShoppingOrderResponseDTO;
import com.levi9.code9.shoppingservice.model.ShoppingItem;
import com.levi9.code9.shoppingservice.model.ShoppingOrder;

public class Utils {

	public static Map<String, String> parseResponseToString(String responseBody) {
		Map<String, String> map = (HashMap<String, String>) Arrays
				.asList(responseBody.substring(1, responseBody.length() - 1).split(",")).stream().map(s -> s.split(":"))
				.collect(Collectors.toMap(e -> e[0], e -> e[1]));

		return map;
	}

	public static List<BookQuantityReductionRequestDTO> getQuantityReductionListFromCart(Set<ShoppingItem>  shoppingItems) {
		List<BookQuantityReductionRequestDTO> quantityReductionList = new ArrayList<BookQuantityReductionRequestDTO>();
		shoppingItems.forEach(item -> {
			quantityReductionList.add(new BookQuantityReductionRequestDTO(item));
		});
		return quantityReductionList;
	}
	

}
