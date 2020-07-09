package com.levi9.code9.authservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.authservice.dto.request.UserRequestDto;
import com.levi9.code9.authservice.dto.response.UserResponseDto;
import com.levi9.code9.authservice.service.UserService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@RestController
@Slf4j
@RequestMapping(value = "/api/v1/auth/users/")
public class UserController {

	@Autowired
	private UserService _userService;
	

	@PostMapping(value = "")
	public UserResponseDto addUser(@Valid @RequestBody UserRequestDto signupDto) {
		return getUserService().registerUser(signupDto);

	}

//	@GetMapping(value = "")
//	public List<UserResponseDto> getAllUsers() {
//		return getUserService().getAllUsers();
//
//	}
//
//	@GetMapping(value = "{id}")
//	public UserResponseDto getUserById(@PathVariable("id") Long id) {
//		return getUserService().getUserById(id);
//
//	}

	@PutMapping(value = "{id}")
	public UserResponseDto updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserRequestDto userDto) {
		return getUserService().updateUser(id, userDto);

	}

	@DeleteMapping(value = "{id}")
	public boolean deleteUser(@PathVariable("id") Long id) {
		return getUserService().deleteUser(id);

	}

}
