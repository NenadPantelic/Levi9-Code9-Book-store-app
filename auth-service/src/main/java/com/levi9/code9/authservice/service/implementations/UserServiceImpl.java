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

import com.levi9.code9.authservice.dto.request.UserRequestDto;
import com.levi9.code9.authservice.dto.response.UserResponseDto;
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

	public UserResponseDto registerUser(UserRequestDto userDto) {
		log.info("Adding new user...");
		User user = getUserMapper().mapUserDtoToUser(userDto);
		user.setPassword(getPasswordEncoder().encode(userDto.getPassword()));
		user.setRole(getRoleRepository().findById(userDto.getRoleId()).get());
		user = getUserRepository().save(user);
		UserResponseDto dto = getUserMapper().userToUserDto(user);
		return dto;
	}

	public List<UserResponseDto> getAllUsers() {
		log.info("Fetching all users...");
		return getUserMapper().usersToUsersDto(getUserRepository().findAll());
	}

	public User fetchUserById(Long id) {
		log.info("Fetching user with id " + id);
		Optional<User> user = getUserRepository().findById(id);
		if (!user.isPresent()) {
			throw new ResourceNotFoundException("The user with the given id doesn't exist.");
		}
		return user.get();
	}

	public UserResponseDto getUserById(Long id) {
		return getUserMapper().userToUserDto(fetchUserById(id));
	}

	@Transactional
	public UserResponseDto updateUser(Long id, UserRequestDto userDto) {
		log.info("Updating user...");
		User user = fetchUserById(id);
		User updatedUser = getUserMapper().mapUserDtoToUser(userDto);
		updatedUser.setId(id);
		updatedUser.setRole(getRoleRepository().findById(userDto.getRoleId()).get());
		updatedUser.setPassword(getPasswordEncoder().encode(userDto.getPassword()));
		updatedUser.setCreatedAt(user.getCreatedAt());
		updatedUser = getUserRepository().save(updatedUser);
		return getUserMapper().userToUserDto(updatedUser);

	}

	@Transactional
	public boolean deleteUser(Long id) {
		getUserRepository().deleteById(id);
		return true;

	}

}
