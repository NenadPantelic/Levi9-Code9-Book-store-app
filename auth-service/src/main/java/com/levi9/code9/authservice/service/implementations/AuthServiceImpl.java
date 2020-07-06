package com.levi9.code9.authservice.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.levi9.code9.authservice.model.User;
import com.levi9.code9.authservice.repository.UserRepository;
import com.levi9.code9.authservice.service.AuthService;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Accessors(prefix="_")
@Getter
@Slf4j
@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private UserRepository _userRepository;
	
	
	@Override
	public User findUserByUsername(String username) {
		return getUserRepository().findUserBy_username(username);
	}

}
