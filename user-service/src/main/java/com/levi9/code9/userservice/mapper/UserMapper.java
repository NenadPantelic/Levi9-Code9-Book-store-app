package com.levi9.code9.userservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.levi9.code9.userservice.dto.request.UserRequestDto;
import com.levi9.code9.userservice.dto.response.UserResponseDto;
import com.levi9.code9.userservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mappings({ @Mapping(target = "roles", expression = "java(user.getStringRoles())") })
	UserResponseDto userToUserDto(User user);

	List<UserResponseDto> usersToUsersDto(List<User> users);

	User mapUserDtoToUser(UserRequestDto dto);

}
