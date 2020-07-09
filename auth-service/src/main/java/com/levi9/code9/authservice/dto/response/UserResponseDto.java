package com.levi9.code9.authservice.dto.response;

import com.levi9.code9.authservice.model.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Accessors(prefix="_")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private Gender gender;
	private String role;
}
