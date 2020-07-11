package com.levi9.code9.userservice.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix="_")
public class RoleRequestDTO {
	private Long _id;
	private String _description;
}
