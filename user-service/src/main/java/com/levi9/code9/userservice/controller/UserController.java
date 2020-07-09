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

import com.levi9.code9.userservice.client.AuthServiceClient;
import com.levi9.code9.userservice.dto.request.UserRequestDto;
import com.levi9.code9.userservice.dto.response.UserResponseDto;
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

	@Autowired
	private AuthServiceClient _authServiceClient;

	// TODO: add propper validator - validate if the action in another micro-service
	// is successful
	@PostMapping(value = "")
	public UserResponseDto registerUser(@Valid @RequestBody UserRequestDto signupDto) {
		UserResponseDto newUser = getUserService().registerUser(signupDto);
		UserResponseDto registeredUserInAuth = getAuthServiceClient().registerUser(signupDto);
		if (registeredUserInAuth != null) {
			log.info("User successfully registered in auth microservice!");
		} else {
			log.error("User is notregistered in auth microservice!");
		}
		return newUser;

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
	public UserResponseDto updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserRequestDto userDto) {
		UserResponseDto updatedUser = getUserService().updateUser(id, userDto);
		UserResponseDto updateUserInAuthService = getAuthServiceClient().updateUser(id, userDto);
		return updatedUser;

	}

	// possible problem - disagreement between ids in services - should be
	// standardized and normalized
	@DeleteMapping(value = "{id}")
	public boolean deleteUser(@PathVariable("id") Long id) {
		if (getAuthServiceClient().deleteUser(id)) {
			log.info("User successfully removed from auth microservice!");
		} else {
			log.error("Action failled! User is not removed auth microservice!");
		}
		return getUserService().deleteUser(id);

	}

}
