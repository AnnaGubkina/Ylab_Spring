package com.edu.ulab.app_ylab.mapper;

import com.edu.ulab.app_ylab.dto.UserDto;
import com.edu.ulab.app_ylab.entity.Person;
import com.edu.ulab.app_ylab.web.request.UserRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-26T22:45:23+0300",
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
    public Person personDtoToUserEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        Person person = new Person();

        person.setId( userDto.getId() );
        person.setFullName( userDto.getFullName() );
        person.setTitle( userDto.getTitle() );
        person.setAge( userDto.getAge() );

        return person;
    }

    @Override
    public UserDto personEntityToUserDto(Person person) {
        if ( person == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( person.getId() );
        userDto.setFullName( person.getFullName() );
        userDto.setTitle( person.getTitle() );
        userDto.setAge( person.getAge() );

        return userDto;
    }
}
