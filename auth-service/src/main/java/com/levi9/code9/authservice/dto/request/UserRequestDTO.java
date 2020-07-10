package com.levi9.code9.authservice.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.levi9.code9.authservice.model.Gender;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix="_")
public class UserRequestDTO {

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
	@NotNull
	private Long _roleId;
	
}
