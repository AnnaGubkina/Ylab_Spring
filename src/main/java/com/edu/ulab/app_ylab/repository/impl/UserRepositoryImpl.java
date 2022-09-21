package com.edu.ulab.app_ylab.repository.impl;

import com.edu.ulab.app_ylab.entity.User;
import com.edu.ulab.app_ylab.repository.UserRepository;
import com.edu.ulab.app_ylab.storage.Storage;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Storage storage;

    public UserRepositoryImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void saveUser(User user) {
        storage.saveUser(user);

    }

    @Override
    public User getUserById(Long id) {
      return storage.getUser(id);
    }

    @Override
    public User updateUser(User user) {
      return storage.updateUser(user);
    }

    @Override
    public Map<Long, User> getAllUsers() {
        return storage.getAllUsers();
    }

    @Override
    public void deleteUserById(Long id) {
        storage.removeUser(id);
    }
}
