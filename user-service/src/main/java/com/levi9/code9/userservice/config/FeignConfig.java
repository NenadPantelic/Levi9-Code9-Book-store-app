package com.levi9.code9.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.levi9.code9.userservice.security.JwtTokenProvider;

import feign.RequestInterceptor;

@Component
public class FeignConfig {
	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.header("Content-Type", "application/json");
			requestTemplate.header("Accept", "application/json");
			requestTemplate.header("Authorization", JwtTokenProvider.JWT_TOKEN);
			
		};
	}

}
