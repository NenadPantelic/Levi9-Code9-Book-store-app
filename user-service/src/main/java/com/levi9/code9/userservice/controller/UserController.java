package com.levi9.code9.userservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.userservice.dto.request.UserRequestDto;
import com.levi9.code9.userservice.dto.response.UserResponseDto;
import com.levi9.code9.userservice.model.User;
import com.levi9.code9.userservice.service.UserService;

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
	public UserResponseDto registerUser(@Valid @RequestBody UserRequestDto signupDto) {
		return getUserService().registerUser(signupDto);

	}

	@GetMapping(value = "")
	public List<UserResponseDto> getAllUsers() {
		return getUserService().getAllUsers();

	}

	@GetMapping(value = "{id}")
	public UserResponseDto getUserById(@PathVariable("id") Long id) {
		return getUserService().getUserById(id);

	}

	@PutMapping(value = "{id}")
	public UserResponseDto updateUser(@PathVariable("id") Long id, @RequestBody UserRequestDto userDto) {
		return getUserService().updateUser(id, userDto);

	}

	@DeleteMapping(value = "{id}")
	public boolean deleteUser(@PathVariable("id") Long id) {
		return getUserService().deleteUser(id);

	}

}
