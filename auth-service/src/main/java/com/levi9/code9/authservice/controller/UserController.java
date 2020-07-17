package com.levi9.code9.authservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.authservice.dto.request.UserRequestDTO;
import com.levi9.code9.authservice.dto.response.UserResponseDTO;
import com.levi9.code9.authservice.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Getter
@RestController
@RequestMapping(value = "/api/v1/auth/users/")
@PreAuthorize("hasAuthority('ADMIN')")
@Api(tags = "UserEndpoints")
public class UserController {

	@Autowired
	private UserService _userService;

	@ApiOperation(value = "Add a new user")
	@PostMapping(value = "")
	public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO signupDto) {
		return getUserService().createUser(signupDto);

	}

	@ApiOperation(value = "Get all users")
	@GetMapping(value = "")
	public List<UserResponseDTO> getUsers() {
		return getUserService().getAllUsers();

	}

	@ApiOperation(value = "Get a specific user")
	@GetMapping(value = "{id}")
	public UserResponseDTO getUserById(@PathVariable("id") Long id) {
		return getUserService().getUserById(id);

	}

	@ApiOperation(value = "Update a specific user")
	@PutMapping(value = "{id}")
	public UserResponseDTO updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserRequestDTO userDto) {
		return getUserService().updateUser(id, userDto);

	}

	@ApiOperation(value = "Delete a specific user")
	@DeleteMapping(value = "{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		getUserService().deleteUser(id);

	}

}
