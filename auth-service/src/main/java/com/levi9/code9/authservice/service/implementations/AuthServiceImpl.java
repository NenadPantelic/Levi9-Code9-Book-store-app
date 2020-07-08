package com.levi9.code9.authservice.service.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.levi9.code9.authservice.dto.request.SigninRequestDto;
import com.levi9.code9.authservice.dto.response.SigninResponseDto;
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
	public Optional<User> findUserByUsername(String username) {
		return getUserRepository().findUserBy_username(username);

	}

	public SigninResponseDto signin(SigninRequestDto credentialsDto) {
		try {
			String username = credentialsDto.getUsername();
			String password = credentialsDto.getPassword();
			log.info("Authentication in progress.....");

			getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
			log.info("HERE");

			Optional<User> user = findUserByUsername(username);
			String token = "";
			String role = null;

			if (user.isPresent() == true) {
				role = user.get().getRole().getAuthority();
				String pwd = user.get().getPassword();
				if (getPasswordEncoder().matches(password, pwd)) {
					token = getTokenProvider().createToken(username, role);
				} else {
					throw new BadCredentialsException("Invalid username/password supplied!");
				}

			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			SigninResponseDto responseDto = new SigninResponseDto(username, token, role);
			return responseDto;
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
}
