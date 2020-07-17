package com.levi9.code9.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.levi9.code9.userservice.config.FeignConfig;
import com.levi9.code9.userservice.dto.request.UserRequestDTO;
import com.levi9.code9.userservice.dto.response.UserResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@FeignClient(name = "auth-service", configuration = { FeignConfig.class })
@Api(tags = "ClientUserEndpoints")
@RequestMapping(value = "/api/v1/auth/users/")
public interface AuthServiceClient {

	@ApiOperation(value="Create a user in Auth microservice")
	@PostMapping(value = "")
	public UserResponseDTO createUser(@RequestBody UserRequestDTO userSignupDTO);

	@ApiOperation(value="Update a user in Auth microservice")
	@PutMapping(value = "{id}")
	public UserResponseDTO updateUser(@PathVariable("id") Long id, @RequestBody UserRequestDTO userDTO);

	@ApiOperation(value="Delete a user in Auth microservice")
	@DeleteMapping(value = "{id}")
	public void deleteUser(@PathVariable("id") Long id);
	

}
