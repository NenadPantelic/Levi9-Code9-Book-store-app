package com.levi9.code9.authservice.service;

import com.levi9.code9.authservice.model.User;

public interface AuthService {
	public User findUserByUsername(String username);

}
