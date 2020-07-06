package com.levi9.code9.authservice.dto;

import com.levi9.code9.authservice.model.Gender;

import lombok.Data;

@Data
public class UserDto {

	private String _firstName;
	private String _lastName;
	private String _username;
	private String _email;
	private Gender _gender;
}