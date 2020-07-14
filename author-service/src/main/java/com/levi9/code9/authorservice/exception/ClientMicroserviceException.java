package com.levi9.code9.authorservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ClientMicroserviceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClientMicroserviceException(String exception) {
		super(exception);
	}

}
