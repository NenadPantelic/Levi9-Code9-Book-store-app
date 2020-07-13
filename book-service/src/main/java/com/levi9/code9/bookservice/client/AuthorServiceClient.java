package com.levi9.code9.bookservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.levi9.code9.bookservice.config.FeignConfig;
import com.levi9.code9.bookservice.dto.request.BookAuthorsRequestDTO;
import com.levi9.code9.bookservice.dto.response.AuthorResponseDTO;

@FeignClient(name = "author-service" , configuration = { FeignConfig.class })
//@RequestMapping(value = "/api/v1/authors/")
public interface AuthorServiceClient {

	@PostMapping(value = "/api/v1/authors/book-authors/")
	public void addBookAuthors(@RequestBody BookAuthorsRequestDTO bookAuthors);// @RequestHeader(value = "Authorization", required = true) String authorizationHeader);

	@GetMapping(value = "/api/v1/authors/book-authors/", params = "bookId")
	public List<AuthorResponseDTO> getBookAuthors(@RequestParam Long bookId);
			//@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

	@DeleteMapping(value = "book-authors/")
	public void removeBookAuthors(@RequestBody BookAuthorsRequestDTO bookAuthors);

	@PutMapping(value = "book-authors/")
	public void replaceBookAuthors(@RequestBody BookAuthorsRequestDTO bookAuthors);
	
	@GetMapping(value="/api/v1/authors/test")
	public boolean test(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

}
