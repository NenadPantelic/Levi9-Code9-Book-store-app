package com.levi9.code9.authservice.dto.response;

import java.util.List;

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
public class UserResponseDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private Gender gender;
	private List<String> roles;
}
