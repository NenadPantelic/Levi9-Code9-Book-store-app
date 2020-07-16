package com.levi9.code9.bookservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientProductQuantityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InsufficientProductQuantityException(String message) {
		super(message);
	}
}
