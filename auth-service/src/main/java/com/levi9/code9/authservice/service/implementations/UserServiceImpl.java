package com.levi9.code9.authservice.service.implementations;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
//@Transactional
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

	public UserResponseDTO registerUser(UserRequestDTO userDto) {
		log.info("Adding new user...");
		User user = getUserMapper().mapUserDTOToUser(userDto);
		user.setPassword(getPasswordEncoder().encode(userDto.getPassword()));
		user.addRole(getRoleRepository().findById(userDto.getRoleId()).get());
		user = getUserRepository().save(user);
		UserResponseDTO dto = getUserMapper().mapUserToUserDTO(user);
		return dto;
	}

	public List<UserResponseDTO> getAllUsers() {
		log.info("Fetching all users...");
		return getUserMapper().mapUserListToUserDTOList(getUserRepository().findAll());
	}

	public User fetchUserById(Long id) {
		log.info("Fetching user with id " + id);
		Optional<User> user = getUserRepository().findById(id);
		if (!user.isPresent()) {
			throw new ResourceNotFoundException("The user with the given id doesn't exist.");
		}
		return user.get();
	}

	public UserResponseDTO getUserById(Long id) {
		return getUserMapper().mapUserToUserDTO(fetchUserById(id));
	}

	@Transactional
	public UserResponseDTO updateUser(Long id, UserRequestDTO userDto) {
		log.info("Updating user...");
		User user = fetchUserById(id);
		User updatedUser = getUserMapper().mapUserDTOToUser(userDto);
		updatedUser.setId(id);
		updatedUser.addRole(getRoleRepository().findById(userDto.getRoleId()).get());
		updatedUser.setPassword(getPasswordEncoder().encode(userDto.getPassword()));
		updatedUser.setCreatedAt(user.getCreatedAt());
		updatedUser = getUserRepository().save(updatedUser);
		return getUserMapper().mapUserToUserDTO(updatedUser);

	}

	@Transactional
	public boolean deleteUser(Long id) {
		getUserRepository().deleteById(id);
		return true;

	}

}
