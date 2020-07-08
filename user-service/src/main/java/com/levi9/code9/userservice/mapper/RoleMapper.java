package com.levi9.code9.userservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.levi9.code9.userservice.dto.response.RoleResponseDto;
import com.levi9.code9.userservice.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

	public List<RoleResponseDto> mapRolesToRoleResponseDtos(Iterable<Role> role);
}
