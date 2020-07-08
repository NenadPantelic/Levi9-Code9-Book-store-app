package com.levi9.code9.userservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import com.levi9.code9.userservice.dto.request.UserRequestDto;
import com.levi9.code9.userservice.dto.response.UserResponseDto;
import com.levi9.code9.userservice.model.User;
import com.levi9.code9.userservice.repository.RoleRepository;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	@Mappings({
        @Mapping(target="role", expression="java(user.getRole().getDescription())")
    })
	UserResponseDto userToUserDto(User user);

	List<UserResponseDto> usersToUsersDto(List<User> users);

//	@Mappings({
//        @Mapping(target="_role", expression="java(_roleRepository.findBy_description(dto.role))")
//    })
	User mapUserDtoToUser(UserRequestDto dto);

}
