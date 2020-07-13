package com.levi9.code9.authorservice.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
public class AuthorResponseDTO {
	private Long _id;
	private String _firstName;
	private String _lastName;
	private String _resume;

}
