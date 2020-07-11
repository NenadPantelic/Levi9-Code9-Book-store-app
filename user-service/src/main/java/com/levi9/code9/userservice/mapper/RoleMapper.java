package com.levi9.code9.userservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.levi9.code9.userservice.dto.request.RoleRequestDTO;
import com.levi9.code9.userservice.dto.response.RoleResponseDTO;
import com.levi9.code9.userservice.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

	public RoleResponseDTO mapToDTO(Role role);
	public Role mapToEntity(RoleRequestDTO roleRequestDTO);
	public List<RoleResponseDTO> mapToDTOList(Iterable<Role> role);
}
