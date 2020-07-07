package com.levi9.code9.authservice.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.levi9.code9.authservice.dto.request.UserRegisterRequestDto;
import com.levi9.code9.authservice.dto.response.UserResponseDto;
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
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	private UserRepository _userRepository;

	@Autowired
	private RoleRepository _roleRepository;

	@Autowired
	private UserMapper _userMapper;

	@Autowired
	PasswordEncoder _passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = getUserRepository().findUserBy_username(username);
		if (user.isPresent() == true) {
			return user.get();
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found");
		}

	}

	public UserResponseDto registerUser(UserRegisterRequestDto signupDto) {
		log.info("Adding new user...");
		User user = User.builder().firstName(signupDto.getFirstName()).lastName(signupDto.getLastName())
				.username(signupDto.getUsername()).email(signupDto.getEmail())
				.password(getPasswordEncoder().encode(signupDto.getPassword())).gender(signupDto.getGender())
				.role(getRoleRepository().findBy_description("BUYER")).build();
		try {
			getUserRepository().save(user);
		} catch (Exception ex) {
			log.info("An error occured");
			return null;
		}
		return getUserMapper().userToUserDto(user);
		// return user;
	}

	public List<User> getAllUsers() {
		log.info("Fetching all users...");
		return getUserRepository().findAll();
	}
}
