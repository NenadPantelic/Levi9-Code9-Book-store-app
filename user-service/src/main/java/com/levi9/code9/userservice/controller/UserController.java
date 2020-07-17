package com.levi9.code9.userservice.controller;

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

import com.levi9.code9.userservice.client.AuthServiceClient;
import com.levi9.code9.userservice.client.ShoppingServiceClient;
import com.levi9.code9.userservice.dto.request.UserRequestDTO;
import com.levi9.code9.userservice.dto.response.UserResponseDTO;
import com.levi9.code9.userservice.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@RestController
@Slf4j
@RequestMapping(value = "/api/v1/users/")
@PreAuthorize(" hasAuthority('ADMIN')")
@Api(tags = "UserEndpoints")
public class UserController {

	@Autowired
	private UserService _userService;

	@Autowired
	private AuthServiceClient _authServiceClient;

	@Autowired
	private ShoppingServiceClient _shoppingServiceClient;

	@ApiOperation(value = "Add a new user")
	@PostMapping(value = "")
	public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO signupDto) {
		UserResponseDTO newUser = getUserService().createUser(signupDto);
		log.info("Adding user to auth microservice");
		getAuthServiceClient().createUser(signupDto);
		log.info("User successfully registered in auth microservice!");
		return newUser;

	}

	@ApiOperation(value = "Get all users")
	@GetMapping(value = "")
	public List<UserResponseDTO> getUsers() {
		return getUserService().getAllUsers();

	}

	@ApiOperation(value = "Get a specific user")
	@GetMapping(value = "{id}")
	public UserResponseDTO getUser(@PathVariable("id") Long id) {
		return getUserService().getUserById(id);

	}

	@ApiOperation(value = "Update a specific user")
	@PutMapping(value = "{id}")
	public UserResponseDTO updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserRequestDTO userDto) {
		UserResponseDTO updatedUser = getUserService().updateUser(id, userDto);
		log.info("Updating user in auth microservice");
		getAuthServiceClient().updateUser(id, userDto);
		log.info("User successfully updated in auth microservice!");
		return updatedUser;

	}

	@ApiOperation(value = "Delete a specific user")
	@DeleteMapping(value = "{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		getUserService().deleteUser(id);
		log.info("Request user removal from auth microservice");
		getAuthServiceClient().deleteUser(id);
		log.info("User successfully removed from auth microservice!");
		log.info("Removing shopping carts for this user and updating state of orders invoices...!");
		getShoppingServiceClient().deleteShoppingCartByUserId(id);

	}

}
