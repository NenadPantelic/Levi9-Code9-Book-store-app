package com.levi9.code9.authorservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.levi9.code9.authorservice.config.FeignConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@FeignClient(name = "book-service", configuration = { FeignConfig.class })
@Api(tags = "ClientBookEndpoints")
public interface BookServiceClient {

	@ApiOperation(value="Delete book author from book microservice")
	@DeleteMapping(value = "/api/v1/books/", params="authorId")
	public void deleteBookAuthor(@RequestParam("authorId") Long authorId);


}
