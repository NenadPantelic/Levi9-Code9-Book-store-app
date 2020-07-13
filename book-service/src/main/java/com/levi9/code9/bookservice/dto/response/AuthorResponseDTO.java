package com.levi9.code9.bookservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDTO {
	private Long _id;
	private String _firstName;
	private String _lastName;
	private String _resume;

}
