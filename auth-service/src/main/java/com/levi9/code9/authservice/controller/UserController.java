package com.levi9.code9.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.levi9.code9.authservice.dto.SignupDto;
import com.levi9.code9.authservice.model.User;
import com.levi9.code9.authservice.service.UserService;

import jdk.internal.jline.internal.Log;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
@Accessors(prefix="_")
@Getter
@RestController
@Slf4j
public class UserController {

	@Autowired
	private UserService _userService;
	
	@PostMapping(value="/auth/signup")
	public User registerUser(@RequestBody SignupDto signupDto) {
		log.info("DEbug here");
		User user = getUserService().registerUser(signupDto);
		return user;
		
	}
	

}
