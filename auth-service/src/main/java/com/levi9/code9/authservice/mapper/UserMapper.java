package com.levi9.code9.authservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.levi9.code9.authservice.dto.response.UserResponseDto;
import com.levi9.code9.authservice.model.User;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
	UserResponseDto userToUserDto(User user);

	List<UserResponseDto> usersToUsersDto(List<User> users);

}
