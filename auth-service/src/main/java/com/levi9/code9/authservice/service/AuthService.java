package com.levi9.code9.authservice.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.levi9.code9.authservice.dto.request.SigninRequestDto;
import com.levi9.code9.authservice.dto.response.SigninResponseDto;
import com.levi9.code9.authservice.model.User;

public interface AuthService {
	public Optional<User> findUserByUsername(String username);
	public SigninResponseDto signin(SigninRequestDto credentialsDto);

}
