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
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ADMIN')")
//@RolesAllowd({)
public class UserController {

	@Autowired
	private UserService _userService;

	@Autowired
	private AuthServiceClient _authServiceClient;

	@PostMapping(value = "")
	public UserResponseDto registerUser(@Valid @RequestBody UserRequestDto signupDto) {
		UserResponseDto newUser = getUserService().registerUser(signupDto);
		getAuthServiceClient().registerUser(signupDto);
		log.info("User successfully registered in auth microservice!");
		return newUser;

	}
	//@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ADMIN')")
	@GetMapping(value = "")
	public List<UserResponseDto> getAllUsers() {
		UserDetails details = getUserService().loadUserByUsername("sone");
		if (details != null)
			log.info(details.getAuthorities().toString());
		return getUserService().getAllUsers();

	}

	@GetMapping(value = "{id}")
	public UserResponseDto getUserById(@PathVariable("id") Long id) {
		return getUserService().getUserById(id);

	}

	@PutMapping(value = "{id}")
	public UserResponseDto updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserRequestDto userDto) {
		log.info("User successfully updated in auth microservice!");
		UserResponseDto updatedUser = getUserService().updateUser(id, userDto);
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
