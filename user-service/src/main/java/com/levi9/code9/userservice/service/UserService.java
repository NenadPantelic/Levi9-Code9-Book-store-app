package com.levi9.code9.userservice.service;

import java.util.List;

import com.levi9.code9.userservice.dto.request.UserRequestDTO;
import com.levi9.code9.userservice.dto.response.UserResponseDTO;
import com.levi9.code9.userservice.model.User;

public interface UserService {

	public User buildUser(UserRequestDTO userDTO);

	public UserResponseDTO createUser(UserRequestDTO signupDto);

	public List<UserResponseDTO> getAllUsers();

	public UserResponseDTO getUserById(Long id);

	public UserResponseDTO updateUser(Long id, UserRequestDTO userDTO);

	public void deleteUser(Long id);
}
