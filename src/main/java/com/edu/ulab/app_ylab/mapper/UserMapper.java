package com.edu.ulab.app_ylab.mapper;

import com.edu.ulab.app_ylab.dto.UserDto;
import com.edu.ulab.app_ylab.entity.User;
import com.edu.ulab.app_ylab.web.request.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userRequestToUserDto(UserRequest userRequest);

    UserRequest userDtoToUserRequest(UserDto userDto);

    User userDtoToUserEntity(UserDto userDto);

    UserDto userEntityToUserDto(User user);
}
