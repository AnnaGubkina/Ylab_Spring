package com.edu.ulab.app_ylab.repository;

import com.edu.ulab.app_ylab.entity.User;

import java.util.Map;

public interface UserRepository {

    void saveUser(User user);

    User getUserById(Long id);

    void updateUser(User user);

    Map<Long, User> getAllUsers();

    void deleteUserById(Long id);

}
