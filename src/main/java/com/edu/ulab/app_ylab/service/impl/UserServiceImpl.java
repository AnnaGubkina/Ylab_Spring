package com.edu.ulab.app_ylab.service.impl;

import com.edu.ulab.app_ylab.dto.UserDto;
import com.edu.ulab.app_ylab.entity.Book;
import com.edu.ulab.app_ylab.entity.User;
import com.edu.ulab.app_ylab.mapper.UserMapper;
import com.edu.ulab.app_ylab.repository.BookRepository;
import com.edu.ulab.app_ylab.repository.UserRepository;
import com.edu.ulab.app_ylab.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public static Long USER_ID_GENERATOR = 0L;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, BookRepository bookRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Long id = ++USER_ID_GENERATOR;
        User user = userMapper.userDtoToUserEntity(userDto);
        user.setId(id);
        userRepository.saveUser(user);
        log.info("Created user: {}", user);
        return getUserById(user.getId());
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        List<UserDto> dtoList = getAllUsers();
        List<Book> userBooks = bookRepository.getBookListByUser(userId);
        for (UserDto element : dtoList)
            if (element.getFullName().equals(userDto.getFullName()) && element.getAge() == userDto.getAge()) {
                return element;
            }
        User user = userMapper.userDtoToUserEntity(userDto);
        user.setId(userId);
        user.setBookList(userBooks);
        userRepository.updateUser(user);
        log.info("Updated user: {}", user);
        User updatedUser = userRepository.getUserById(userId);
        return userMapper.userEntityToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.getUserById(id);
        log.info("Get user: {}", user);
        return userMapper.userEntityToUserDto(user);

    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);
        log.info("User deleted: {}", id);
    }


    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers().values().stream()
                .map(userMapper::userEntityToUserDto)
                .toList();
    }
}
