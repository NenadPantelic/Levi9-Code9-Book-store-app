package com.levi9.code9.authservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.authservice.dto.request.SigninRequestDto;
import com.levi9.code9.authservice.dto.response.SigninResponseDto;
import com.levi9.code9.authservice.model.User;
import com.levi9.code9.authservice.security.JwtTokenProvider;
import com.levi9.code9.authservice.service.AuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "AuthenticationEndpoints")
@Accessors(prefix = "_")
@Getter
@RestController
@RequestMapping(value = "/auth/")
public class AuthController {

	@Autowired
	AuthService _authService;

	@ApiOperation(value = "Authenticates a user and returns a token")
	@PostMapping(value = "signin")
	public SigninResponseDto signin(@RequestBody SigninRequestDto credentialsDto) {
		return getAuthService().signin(credentialsDto);
	}

}
