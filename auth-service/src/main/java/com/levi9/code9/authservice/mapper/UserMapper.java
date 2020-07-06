package com.levi9.code9.authservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.levi9.code9.authservice.dto.UserDto;
import com.levi9.code9.authservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
    List<UserDto> usersToUsersDto(List<User> users);
}
