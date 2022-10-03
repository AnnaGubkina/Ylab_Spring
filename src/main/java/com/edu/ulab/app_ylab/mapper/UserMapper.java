package com.edu.ulab.app_ylab.mapper;

import com.edu.ulab.app_ylab.dto.UserDto;
import com.edu.ulab.app_ylab.entity.Person;
import com.edu.ulab.app_ylab.web.request.UserRequest;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userRequestToUserDto(UserRequest userRequest);

    UserRequest userDtoToUserRequest(UserDto userDto);

    Person personDtoToUserEntity(UserDto userDto);

    UserDto personEntityToUserDto(Person person);

}
