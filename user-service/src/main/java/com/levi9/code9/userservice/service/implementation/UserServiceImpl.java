package com.levi9.code9.userservice.service.implementation;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.levi9.code9.userservice.dto.request.UserRequestDTO;
import com.levi9.code9.userservice.dto.response.UserResponseDTO;
import com.levi9.code9.userservice.exception.ResourceNotFoundException;
import com.levi9.code9.userservice.mapper.UserMapper;
import com.levi9.code9.userservice.model.User;
import com.levi9.code9.userservice.repository.RoleRepository;
import com.levi9.code9.userservice.repository.UserRepository;
import com.levi9.code9.userservice.security.JwtTokenProvider;
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
	public User buildUser(UserRequestDTO userDTO) {
		User user = getUserMapper().mapToEntity(userDTO);
		user.setPassword(getPasswordEncoder().encode(userDTO.getPassword()));
		user.addRole(getRoleRepository().findById(userDTO.getRoleId()).get());
		return user;
	}

	@Override
	public UserResponseDTO createUser(UserRequestDTO userDTO) {
		log.info("Adding new user...");
		User user = buildUser(userDTO);
		user = getUserRepository().save(user);
		log.info("User successfully added.");
		UserResponseDTO dto = getUserMapper().mapToDTO(user);
		return dto;
	}

	@Override
	public List<UserResponseDTO> getAllUsers() {
		log.info("Fetching all users...");
		return getUserMapper().mapToDTOList(getUserRepository().findAll());
	}

	@Override
	public UserResponseDTO getUserById(Long id) {
		return getUserMapper().mapToDTO(fetchUserById(id));
	}

	public User fetchUserById(Long id) {
		log.info("Fetching user with id = " + id);
		User user = getUserRepository().findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("The user with the given id doesn't exist."));
		return user;
	}

	@Override
	public UserResponseDTO updateUser(Long id, UserRequestDTO userDTO) {
		log.info("Updating user with the id = " + id);
		User user = fetchUserById(id);
		User updatedUser = buildUser(userDTO);
		updatedUser.setId(id);
		updatedUser = getUserRepository().save(updatedUser);
		log.info("User successfully updated.");
		return getUserMapper().mapToDTO(updatedUser);

	}

	public void deleteUser(Long id) {
		log.info("Deleting the user with the id =" + id);
		User user = fetchUserById(id);
		getUserRepository().delete(user);
		log.info("User successfully deleted.");

	}

}
