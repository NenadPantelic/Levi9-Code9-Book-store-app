package com.levi9.code9.authservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.authservice.dto.request.SigninRequestDTO;
import com.levi9.code9.authservice.dto.response.SigninResponseDTO;
import com.levi9.code9.authservice.service.AuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Getter
@RestController
@RequestMapping(value = "/auth/")
@Api(tags = "AuthenticationEndpoints")
public class AuthController {

	@Autowired
	AuthService _authService;

	@ApiOperation(value = "Authenticates a user")
	@PostMapping(value = "signin")
	public SigninResponseDTO signin(@Valid @RequestBody SigninRequestDTO credentialsDto) {
		return getAuthService().signin(credentialsDto);
	}

}
