package com.levi9.code9.authservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.levi9.code9.authservice.dto.request.UserRegisterRequestDto;
import com.levi9.code9.authservice.dto.response.UserResponseDto;
import com.levi9.code9.authservice.model.User;

public interface UserService {
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	public UserResponseDto registerUser(UserRegisterRequestDto signupDto);
}
