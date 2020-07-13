package com.levi9.code9.authservice.service.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.levi9.code9.authservice.dto.request.SigninRequestDTO;
import com.levi9.code9.authservice.dto.response.SigninResponseDTO;
import com.levi9.code9.authservice.model.User;
import com.levi9.code9.authservice.repository.UserRepository;
import com.levi9.code9.authservice.security.JwtTokenProvider;
import com.levi9.code9.authservice.service.AuthService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix = "_")
@Getter
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private UserRepository _userRepository;

	@Autowired
	private AuthenticationManager _authenticationManager;

	@Autowired
	private JwtTokenProvider _tokenProvider;

	@Autowired
	PasswordEncoder _passwordEncoder;

	@Override
	public User findUserByUsername(String username) {
		log.info("Fetching a user with the username = " + username);
		User user = getUserRepository().findUserBy_username(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found!"));
		return user;
	}

	@Override
	public SigninResponseDTO signin(SigninRequestDTO credentialsDto) {
		try {
			String username = credentialsDto.getUsername();
			String password = credentialsDto.getPassword();
			log.info("Authentication in progress.....");

			getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
			User user = findUserByUsername(username);
			String token = "";
			List<String> roles = null;

			roles = user.getStringRoles();
			String pwd = user.getPassword();
			if (getPasswordEncoder().matches(password, pwd)) {
				token = getTokenProvider().createToken(user.getId(), username, roles);
			} else {
				throw new BadCredentialsException("Invalid username/password supplied!");
			}

			SigninResponseDTO responseDto = new SigninResponseDTO(username, token, roles);
			return responseDto;
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
}
