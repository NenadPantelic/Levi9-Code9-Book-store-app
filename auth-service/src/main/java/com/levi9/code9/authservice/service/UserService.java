package com.levi9.code9.authservice.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.levi9.code9.authservice.dto.request.UserRequestDto;
import com.levi9.code9.authservice.dto.response.UserResponseDto;

public interface UserService {
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	public UserResponseDto registerUser(UserRequestDto signupDto);
	public List<UserResponseDto> getAllUsers();
	public UserResponseDto getUserById(Long id);
	public UserResponseDto updateUser(Long id, UserRequestDto userDto);
	public boolean deleteUser(Long id);
}
