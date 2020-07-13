package com.levi9.code9.bookservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.levi9.code9.bookservice.security.JwtTokenProvider;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class FeignConfig {

	private static final String JWT_PREFIX = "Bearer ";

	@Bean
	public RequestInterceptor requestInterceptor() {
		System.out.println("DEBUG");
		System.out.println(JwtTokenProvider.jwtToken);
		return requestTemplate -> {
			requestTemplate.header("Content-Type", "application/json");
			requestTemplate.header("Accept", "application/json");
			requestTemplate.header("Authorization", JWT_PREFIX + JwtTokenProvider.USER_CONTEXT.get().getToken());//JwtTokenProvider.jwtToken);

		};
	}

}
