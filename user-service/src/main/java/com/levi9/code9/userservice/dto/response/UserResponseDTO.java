package com.levi9.code9.userservice.dto.response;

import java.util.List;

import com.levi9.code9.userservice.model.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

	private Long _id;
	private String _firstName;
	private String _lastName;
	private String _username;
	private String _email;
	private Gender _gender;
	private List<String> _roles;
}
