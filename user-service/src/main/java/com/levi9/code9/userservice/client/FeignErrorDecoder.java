package com.levi9.code9.userservice.client;

import java.io.IOException;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.levi9.code9.userservice.exception.ClientMicroserviceException;
import com.levi9.code9.userservice.utils.Utils;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

	private static final String ERROR_MESSAGE = "An error occured in the auth client microservice. Original response: ";

	@Override
	public Exception decode(String methodKey, Response response) {
		log.info("An error occured in client microservice");
		String originalResponseMessage = "";
		if (response.status() > 299) {
			try {
				originalResponseMessage = IOUtils.toString(response.body().asInputStream());
			} catch (IOException e) {

			}

		}
		return new ClientMicroserviceException(
				ERROR_MESSAGE + Utils.parseResponseToString(originalResponseMessage).toString());
	}

}