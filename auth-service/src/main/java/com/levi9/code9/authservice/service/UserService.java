package com.levi9.code9.authservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.levi9.code9.authservice.dto.request.UserRegisterRequestDto;
import com.levi9.code9.authservice.dto.response.UserResponseDto;

public interface UserService {
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
