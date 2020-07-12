package com.levi9.code9.authservice.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.levi9.code9.authservice.dto.request.SigninRequestDTO;
import com.levi9.code9.authservice.dto.response.SigninResponseDTO;
import com.levi9.code9.authservice.model.User;

public interface AuthService {
	public User findUserByUsername(String username);
	public SigninResponseDTO signin(SigninRequestDTO credentialsDto);

}
