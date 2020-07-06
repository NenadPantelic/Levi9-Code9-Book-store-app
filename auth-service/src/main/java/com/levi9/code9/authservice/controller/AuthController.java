package com.levi9.code9.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.authservice.dto.SigninRequestDto;
import com.levi9.code9.authservice.dto.SigninResponseDto;
import com.levi9.code9.authservice.model.User;
import com.levi9.code9.authservice.security.JwtTokenProvider;
import com.levi9.code9.authservice.service.AuthService;

import jdk.internal.jline.internal.Log;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;


@Accessors(prefix = "_")
@Getter
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthController {

	@Autowired
	AuthService _authService;

	@Autowired
	AuthenticationManager _authenticationManager;

	@Autowired
	JwtTokenProvider _tokenProvider;

	@GetMapping(value = "")
	public Boolean test() {
		return true;
	}

//	@ApiOperation(value = "Authenticates a user and returns a token")
//	@SuppressWarnings("rawtypes")
	@SuppressWarnings("unused")
	@PostMapping(value = "signin")
	public SigninResponseDto signin(@RequestBody SigninRequestDto credentialsDto) {
		try {
			String username = credentialsDto.getUsername();
			String pasword = credentialsDto.getPassword();
			log.info("Authentication in progress.....");

			getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, pasword));

			User user = getAuthService().findUserByUsername(username);
			String token = "";
			String role = user.getRole().getAuthority();

			if (user != null) {
				token = getTokenProvider().createToken(username, role);
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			SigninResponseDto responseDto = new SigninResponseDto(username, token, role);
			return responseDto;
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}

}
