package com.levi9.code9.userservice.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.levi9.code9.userservice.dto.request.UserRequestDTO;
import com.levi9.code9.userservice.dto.response.UserResponseDTO;

public interface UserService {
	public UserResponseDTO createUser(UserRequestDTO signupDto);

	public List<UserResponseDTO> getAllUsers();

	public UserResponseDTO getUserById(Long id);

	public UserResponseDTO updateUser(Long id, UserRequestDTO userDto);

	public boolean deleteUser(Long id);
}
