package com.edu.ulab.app_ylab.mapper;

import com.edu.ulab.app_ylab.dto.UserDto;
import com.edu.ulab.app_ylab.entity.User;
import com.edu.ulab.app_ylab.web.request.UserRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-19T21:07:24+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userRequestToUserDto(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setFullName( userRequest.getFullName() );
        userDto.setTitle( userRequest.getTitle() );
        userDto.setAge( userRequest.getAge() );

        return userDto;
    }

    @Override
    public UserRequest userDtoToUserRequest(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserRequest userRequest = new UserRequest();

        userRequest.setFullName( userDto.getFullName() );
        userRequest.setTitle( userDto.getTitle() );
        userRequest.setAge( userDto.getAge() );

        return userRequest;
    }

    @Override
    public User userDtoToUserEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setFullName( userDto.getFullName() );
        user.setTitle( userDto.getTitle() );
        user.setAge( userDto.getAge() );

        return user;
    }

    @Override
    public UserDto userEntityToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setFullName( user.getFullName() );
        userDto.setTitle( user.getTitle() );
        userDto.setAge( user.getAge() );

        return userDto;
    }
}
