package com.levi9.code9.userservice.service;

import java.util.List;

import com.levi9.code9.userservice.dto.request.RoleRequestDTO;
import com.levi9.code9.userservice.dto.response.RoleResponseDTO;

public interface RoleService {

	public RoleResponseDTO createRole(RoleRequestDTO roleDTO);
	public List<RoleResponseDTO> getRoles();
}
