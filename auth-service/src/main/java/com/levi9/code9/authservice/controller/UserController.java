package com.levi9.code9.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.authservice.dto.request.UserRegisterRequestDto;
import com.levi9.code9.authservice.dto.response.UserResponseDto;
import com.levi9.code9.authservice.model.User;
import com.levi9.code9.authservice.service.UserService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@RestController
@Slf4j
@RequestMapping(value = "/api/v1/users/")
public class UserController {

	@Autowired
	private UserService _userService;

	@PostMapping(value = "")
	public UserResponseDto registerUser(@RequestBody UserRegisterRequestDto signupDto) {
		return getUserService().registerUser(signupDto);

	}

}
