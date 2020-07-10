package com.levi9.code9.bookservice.dto.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreRequestDTO {
	@NotNull(message="Genre name must not be null")
	@NotBlank(message="Genre name must not be blank")
	private String _name;
}
