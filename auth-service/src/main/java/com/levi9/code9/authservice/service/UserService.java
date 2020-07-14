package com.levi9.code9.authservice.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.levi9.code9.authservice.dto.request.UserRequestDTO;
import com.levi9.code9.authservice.dto.response.UserResponseDTO;
import com.levi9.code9.authservice.model.User;

public interface UserService {
	public User buildUser(UserRequestDTO userDTO);

	public UserResponseDTO createUser(UserRequestDTO signupDto);

	public List<UserResponseDTO> getAllUsers();

	public UserResponseDTO getUserById(Long id);

	public UserResponseDTO updateUser(Long id, UserRequestDTO userDto);

	public void deleteUser(Long id);

}
