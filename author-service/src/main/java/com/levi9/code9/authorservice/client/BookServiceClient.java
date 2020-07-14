package com.levi9.code9.authorservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.levi9.code9.authorservice.config.FeignConfig;

@FeignClient(name = "book-service", configuration = { FeignConfig.class })
public interface BookServiceClient {


	@DeleteMapping(value = "/api/v1/books/", params="authorId")
	public void deleteBookAuthors(@RequestParam("authorId") Long authorId);


}
