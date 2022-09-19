package com.edu.ulab.app_ylab.repository;

import com.edu.ulab.app_ylab.dto.UserDto;
import com.edu.ulab.app_ylab.entity.User;
import io.micrometer.core.instrument.Tags;

import java.util.List;

public interface UserRepository {

    void saveUser(User user);

    User getUserById(Long id);

    User updateUser(User user);

    List<User> getAllUsers();

    void deleteUserById(Long id);

}
