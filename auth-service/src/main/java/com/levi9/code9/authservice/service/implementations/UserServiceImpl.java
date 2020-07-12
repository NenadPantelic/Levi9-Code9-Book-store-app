package com.levi9.code9.authservice.service.implementations;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.levi9.code9.authservice.dto.request.UserRequestDTO;
import com.levi9.code9.authservice.dto.response.UserResponseDTO;
import com.levi9.code9.authservice.exception.ResourceNotFoundException;
import com.levi9.code9.authservice.mapper.UserMapper;
import com.levi9.code9.authservice.model.User;
import com.levi9.code9.authservice.repository.RoleRepository;
import com.levi9.code9.authservice.repository.UserRepository;
import com.levi9.code9.authservice.service.UserService;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Accessors(prefix = "_")
@Setter
@Getter
@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository _userRepository;

	@Autowired
	private RoleRepository _roleRepository;

	@Autowired
	private UserMapper _userMapper;

	@Autowired
	private PasswordEncoder _passwordEncoder;

	@Override
	public UserResponseDTO createUser(UserRequestDTO userDto) {
		log.info("Adding new user...");
		User user = getUserMapper().mapToEntity(userDto);
		user.setPassword(getPasswordEncoder().encode(userDto.getPassword()));
		user.addRole(getRoleRepository().findById(userDto.getRoleId()).get());
		user = getUserRepository().save(user);
		UserResponseDTO dto = getUserMapper().mapToDTO(user);
		return dto;
	}

	@Override
	public List<UserResponseDTO> getAllUsers() {
		log.info("Fetching all users...");
		return getUserMapper().mapToDTOList(getUserRepository().findAll());
	}

	public User fetchUserById(Long id) {
		log.info("Fetching a user with id = " + id);
		User user = getUserRepository().findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("The user with the given id doesn't exist."));
		return user;
	}

	@Override
	public UserResponseDTO getUserById(Long id) {
		return getUserMapper().mapToDTO(fetchUserById(id));
	}

	@Override
	public UserResponseDTO updateUser(Long id, UserRequestDTO userDto) {
		log.info("Updating the user with the id = " + id);
		User user = fetchUserById(id);
		User updatedUser = getUserMapper().mapToEntity(userDto);
		updatedUser.setId(id);
		updatedUser.addRole(getRoleRepository().findById(userDto.getRoleId()).get());
		updatedUser.setPassword(getPasswordEncoder().encode(userDto.getPassword()));
		updatedUser.setCreatedAt(user.getCreatedAt());
		updatedUser = getUserRepository().save(updatedUser);
		return getUserMapper().mapToDTO(updatedUser);

	}

	@Override
	public boolean deleteUser(Long id) {
		log.info("Deleting the user with the id " + id);
		getUserRepository().deleteById(id);
		return true;

	}

}
