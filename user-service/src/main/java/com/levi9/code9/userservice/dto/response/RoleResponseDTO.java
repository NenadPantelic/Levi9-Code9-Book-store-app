package com.levi9.code9.userservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(prefix="_")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponseDTO {
	private Long _id;
	private String _description;
}
