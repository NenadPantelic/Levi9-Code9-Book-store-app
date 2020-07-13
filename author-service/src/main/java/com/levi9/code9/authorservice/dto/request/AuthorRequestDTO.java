package com.levi9.code9.authorservice.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(prefix = "_")
@Data
public class AuthorRequestDTO {

	@NotBlank(message = "Author's first name must be provided.")
	@Size(max = 255, message = "Length of the author's last name must be between 1 and 255.")
	private String _firstName;

	@NotBlank(message = "Author's last name must be provided.")
	@Size(max = 255, message = "Length of the author's last name must be between 1 and 255.")
	private String _lastName;

	@Size(max = 500, message = "Resume text is too long. It cannot be longer than 500 characters.")
	private String _resume;

}
