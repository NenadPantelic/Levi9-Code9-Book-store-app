package com.levi9.code9.authservice.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
public class SigninRequestDto {

	@NotNull
	@NotEmpty
	private String _username;
	@NotNull
	@NotEmpty
	private String _password;
}
