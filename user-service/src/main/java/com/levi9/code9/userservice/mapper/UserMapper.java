package com.levi9.code9.userservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.levi9.code9.userservice.dto.request.UserRequestDTO;
import com.levi9.code9.userservice.dto.response.UserResponseDTO;
import com.levi9.code9.userservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mappings({ @Mapping(target = "roles", expression = "java(user.getStringRoles())") })
	UserResponseDTO mapToDTO(User user);

	List<UserResponseDTO> mapToDTOList(List<User> users);

	User mapToEntity(UserRequestDTO dto);

}
