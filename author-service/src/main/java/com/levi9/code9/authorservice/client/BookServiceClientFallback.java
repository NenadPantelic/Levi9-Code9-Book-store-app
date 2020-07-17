package com.levi9.code9.authorservice.client;

import org.springframework.stereotype.Component;

@Component
public class BookServiceClientFallback implements BookServiceClient {

	@Override
	public void deleteBookAuthor(Long authorId) {
		
	}

	

}
