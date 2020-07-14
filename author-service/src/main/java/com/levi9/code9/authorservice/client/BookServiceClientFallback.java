package com.levi9.code9.authorservice.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BookServiceClientFallback implements BookServiceClient {

	@Override
	public void deleteBookAuthors(Long authorId) {
		
	}

	

}
