package com.levi9.code9.authservice.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SigninResponseDto {

	private String _username;
	private String _token;
	private List<String> _roles;
}
