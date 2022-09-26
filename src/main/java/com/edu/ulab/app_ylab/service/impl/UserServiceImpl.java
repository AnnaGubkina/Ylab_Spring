package com.edu.ulab.app_ylab.service.impl;

import com.edu.ulab.app_ylab.dto.UserDto;
import com.edu.ulab.app_ylab.entity.Person;
import com.edu.ulab.app_ylab.mapper.UserMapper;
import com.edu.ulab.app_ylab.repository.UserRepository;
import com.edu.ulab.app_ylab.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * This class contains the implementation of methods using Hibernate:
 * 1) createUser
 * 2) updateUser
 * 3) getUserById
 * 4) deleteUserById
 */


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;


    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Create user in database: {}", userDto);
        Person user = userMapper.personDtoToUserEntity(userDto);
        log.info("Mapped user: {}", user);
        Person savedUser = userRepository.save(user);
        log.info("Saved user: {}", savedUser);
        return userMapper.personEntityToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        log.info("Update user by id: {}", userDto);
        UserDto element = getUserById(userId);
        if (element.getFullName().equals(userDto.getFullName()) && element.getAge() == userDto.getAge()) {
            log.info("Return equal user: {}", element);
            return element;
        }
        Person user = userMapper.personDtoToUserEntity(userDto);
        Person updatedUser = userRepository.findPersonById(userId);
        updatedUser.setFullName(user.getFullName());
        updatedUser.setTitle(user.getTitle());
        updatedUser.setAge(user.getAge());
        userRepository.save(updatedUser);
        log.info("Updated user: {}", updatedUser);
        return userMapper.personEntityToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Get user by id: {}", id);
        Person user = userRepository.findPersonById(id);
        log.info("User received: {}", user);
        return userMapper.personEntityToUserDto(user);

    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Delete user by id: {}", id);
        userRepository.deleteById(id);
        log.info("Deleted user with id: {}", id);
    }
}
