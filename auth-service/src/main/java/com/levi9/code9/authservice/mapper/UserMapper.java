package com.levi9.code9.authservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.levi9.code9.authservice.dto.request.UserRequestDto;
import com.levi9.code9.authservice.dto.response.UserResponseDto;
import com.levi9.code9.authservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mappings({ @Mapping(target = "role", expression = "java(user.getRole().getDescription())") })
	UserResponseDto userToUserDto(User user);

	List<UserResponseDto> usersToUsersDto(List<User> users);

	User mapUserDtoToUser(UserRequestDto dto);

}
