package com.levi9.code9.authservice.service;

import com.levi9.code9.authservice.dto.SignupDto;
import com.levi9.code9.authservice.dto.UserDto;
import com.levi9.code9.authservice.model.User;

public interface UserService {
	public User registerUser(SignupDto signupDto);
}
