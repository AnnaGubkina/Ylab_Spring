package com.edu.ulab.app_ylab.service;

import com.edu.ulab.app_ylab.dto.UserDto;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Long userId);

    UserDto getUserById(Long id);

    void deleteUserById(Long id);

}
