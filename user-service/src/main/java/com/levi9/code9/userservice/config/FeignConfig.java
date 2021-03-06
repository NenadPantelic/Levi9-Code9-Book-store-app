package com.levi9.code9.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.levi9.code9.userservice.security.JwtTokenFilter;
import com.levi9.code9.userservice.security.JwtTokenProvider;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FeignConfig {

	private static final String JWT_PREFIX = "Bearer ";

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.header("Content-Type", "application/json");
			requestTemplate.header("Accept", "application/json");
			requestTemplate.header("Authorization", JWT_PREFIX + JwtTokenProvider.USER_CONTEXT.get().getToken());

		};
	}

}
