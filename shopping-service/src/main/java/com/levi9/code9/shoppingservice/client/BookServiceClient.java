package com.levi9.code9.shoppingservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.levi9.code9.shoppingservice.config.FeignConfig;
import com.levi9.code9.shoppingservice.dto.response.BookWithAuthorResponseDTO;

@FeignClient(name = "book-service", configuration = { FeignConfig.class })
public interface BookServiceClient {

	@PostMapping(value = "/api/v1/books/list")
	public List<BookWithAuthorResponseDTO> getBooksByListOfIds(@RequestBody List<Long> ids);
}