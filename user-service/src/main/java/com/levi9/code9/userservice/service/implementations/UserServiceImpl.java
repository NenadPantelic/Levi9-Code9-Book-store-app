package com.levi9.code9.userservice.service.implementations;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.levi9.code9.userservice.client.AuthServiceClient;
import com.levi9.code9.userservice.dto.request.UserRequestDTO;
import com.levi9.code9.userservice.dto.response.UserResponseDTO;
import com.levi9.code9.userservice.exception.ResourceNotFoundException;
import com.levi9.code9.userservice.mapper.UserMapper;
import com.levi9.code9.userservice.model.User;
import com.levi9.code9.userservice.repository.RoleRepository;
import com.levi9.code9.userservice.repository.UserRepository;
import com.levi9.code9.userservice.service.UserService;

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
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	private UserRepository _userRepository;

	@Autowired
	private RoleRepository _roleRepository;

	@Autowired
	private UserMapper _userMapper;

	@Autowired
	private PasswordEncoder _passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = getUserRepository().findUserBy_username(username);
		if (user.isPresent() == true) {
			return user.get();
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}

	}

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
		log.info("Fetching user with id " + id);
		Optional<User> user = getUserRepository().findById(id);
		if (!user.isPresent()) {
			throw new ResourceNotFoundException("The user with the given id doesn't exist.");
		}
		return user.get();
	}

	@Override
	public UserResponseDTO getUserById(Long id) {
		return getUserMapper().mapToDTO(fetchUserById(id));
	}

	@Override
	// @Transactional
	public UserResponseDTO updateUser(Long id, UserRequestDTO userDto) {
		log.info("Updating user...");
		User user = fetchUserById(id);
		User updatedUser = getUserMapper().mapToEntity(userDto);
		updatedUser.setId(id);
		user.addRole(getRoleRepository().findById(userDto.getRoleId()).get());
		updatedUser.setPassword(getPasswordEncoder().encode(userDto.getPassword()));
		updatedUser.setCreatedAt(user.getCreatedAt());
		updatedUser = getUserRepository().save(updatedUser);
		return getUserMapper().mapToDTO(updatedUser);

	}

	// @Transactional
	public boolean deleteUser(Long id) {
		getUserRepository().deleteById(id);
		return true;

	}

}
