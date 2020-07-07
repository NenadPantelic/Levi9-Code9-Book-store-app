package com.levi9.code9.authservice.dto.request;

import javax.validation.constraints.NotBlank;

import com.levi9.code9.authservice.model.Gender;
import com.levi9.code9.authservice.model.Role;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix="_")
public class UserRegisterRequestDto {

	@NotBlank
	private String _firstName;
	@NotBlank
	private String _lastName;
	@NotBlank
	private String _username;
	@NotBlank
	private String _email;
	@NotBlank
	private String _password;
	private Gender _gender;
	
}
