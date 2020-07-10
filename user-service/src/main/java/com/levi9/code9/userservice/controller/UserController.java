package com.levi9.code9.userservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.userservice.client.AuthServiceClient;
import com.levi9.code9.userservice.dto.request.UserRequestDTO;
import com.levi9.code9.userservice.dto.response.UserResponseDTO;
import com.levi9.code9.userservice.service.UserService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@RestController
@Slf4j
@RequestMapping(value = "/api/v1/users/")
@PreAuthorize(" hasAuthority('ADMIN')")
public class UserController {

	@Autowired
	private UserService _userService;

	@Autowired
	private AuthServiceClient _authServiceClient;

	@PostMapping(value = "")
	public UserResponseDTO registerUser(@Valid @RequestBody UserRequestDTO signupDto) {
		UserResponseDTO newUser = getUserService().registerUser(signupDto);
		getAuthServiceClient().registerUser(signupDto);
		log.info("User successfully registered in auth microservice!");
		return newUser;

	}

	@GetMapping(value = "")
	public List<UserResponseDTO> getAllUsers() {
		return getUserService().getAllUsers();

	}

	@GetMapping(value = "{id}")
	public UserResponseDTO getUserById(@PathVariable("id") Long id) {
		return getUserService().getUserById(id);

	}

	@PutMapping(value = "{id}")
	public UserResponseDTO updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserRequestDTO userDto) {
		log.info("User successfully updated in auth microservice!");
		UserResponseDTO updatedUser = getUserService().updateUser(id, userDto);
		getAuthServiceClient().updateUser(id, userDto);
		return updatedUser;

	}

	@DeleteMapping(value = "{id}")
	public boolean deleteUser(@PathVariable("id") Long id) {
		boolean result = getUserService().deleteUser(id);
		getAuthServiceClient().deleteUser(id);
		log.info("User successfully removed from auth microservice!");
		return result;

	}

}
