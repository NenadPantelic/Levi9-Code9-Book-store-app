package com.levi9.code9.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.levi9.code9.userservice.config.FeignConfig;
import com.levi9.code9.userservice.dto.request.UserRequestDto;
import com.levi9.code9.userservice.dto.response.UserResponseDto;

@FeignClient(name = "auth-service", configuration = { FeignConfig.class })
@RequestMapping(value = "/api/v1/auth/users/")
public interface AuthServiceClient {

	@PostMapping(value = "")
	public UserResponseDto registerUser(@RequestBody UserRequestDto signupDto);

	@PutMapping(value = "{id}")
	public UserResponseDto updateUser(@PathVariable("id") Long id, @RequestBody UserRequestDto userDto);

	@DeleteMapping(value = "{id}")
	public boolean deleteUser(@PathVariable("id") Long id);
	

}
