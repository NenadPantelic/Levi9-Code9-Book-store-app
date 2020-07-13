package com.levi9.code9.authservice.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(prefix = "_")
@Getter
@Slf4j
@Service
public class JwtTokenFilter extends OncePerRequestFilter {// GenericFilterBean {

	@Autowired
	private JwtTokenProvider _tokenProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String token = getTokenProvider().resolveToken((HttpServletRequest) request);
		if (token != null && getTokenProvider().validateToken(token)) {
			Authentication auth = getTokenProvider().getAuthentication(token);
			if (auth != null) {
				log.info("Authenticating user....");
				SecurityContextHolder.getContext().setAuthentication(auth);
				getTokenProvider().setUserContext(token);
				
			}
		}
		chain.doFilter(request, response);
	}

}