package com.levi9.code9.userservice.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.levi9.code9.userservice.dto.request.UserRequestDto;
import com.levi9.code9.userservice.dto.response.UserResponseDto;
import com.levi9.code9.userservice.model.User;

public interface UserService {
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	public UserResponseDto registerUser(UserRequestDto signupDto);
	public List<User> getAllUsers();
	public User getUserById(Long id);
	public User updateUser(Long id, UserRequestDto userDto);
	public boolean deleteUser(Long id);
}
