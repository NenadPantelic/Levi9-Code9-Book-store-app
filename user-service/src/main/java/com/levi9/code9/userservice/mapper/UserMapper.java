package com.levi9.code9.userservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.levi9.code9.userservice.dto.request.UserRequestDto;
import com.levi9.code9.userservice.dto.response.UserResponseDto;
import com.levi9.code9.userservice.model.User;

//@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
//	@Mappings({
//		  @Mapping(target="id", source = "user._id"),
//		  @Mapping(target="firstName", source = "user._firstName"),
//		  //@Mapping(target="employeeStartDt", source = "entity.startDt",
//		   //        dateFormat = "dd-MM-yyyy HH:mm:ss")
//		  })
	UserResponseDto userToUserDto(User user);

	List<UserResponseDto> usersToUsersDto(List<User> users);
	User mapUserDtoToUser (UserRequestDto dto);

}
