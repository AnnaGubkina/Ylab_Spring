package com.edu.ulab.app_ylab.service.impl;

import com.edu.ulab.app_ylab.dto.UserDto;
import com.edu.ulab.app_ylab.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

/**
 * This class contains the implementation of methods using JdbcTemplate:
 * 1) createUser
 * 2) updateUser
 * 3) getUserById
 * 4) deleteUserById
 */

@Slf4j
@Service
public class UserServiceImplTemplate implements UserService {

    private final JdbcTemplate jdbcTemplate;

    public UserServiceImplTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Create user in database: {}", userDto);
        final String INSERT_SQL = "INSERT INTO PERSON(FULL_NAME, TITLE, AGE) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, userDto.getFullName());
                    ps.setString(2, userDto.getTitle());
                    ps.setLong(3, userDto.getAge());
                    return ps;
                }, keyHolder);

        userDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.info("Created user: {}", userDto);
        return userDto;

    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        log.info("Update user by id: {}", userDto);
        UserDto element = getUserById(userId);
        if (element.getFullName().equals(userDto.getFullName()) && element.getAge() == userDto.getAge()) {
            log.info("Return equal user: {}", element);
            return element;
        }
        jdbcTemplate.update("update person SET full_name =  ?, title = ?, age = ? WHERE id = ?",
                userDto.getFullName(),
                userDto.getTitle(),
                userDto.getAge(), userId);
        log.info("Updated user: {}", userDto);
        return getUserById(userId);
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Get user by id: {}", id);
        final String SELECT_SQL = "SELECT * FROM PERSON WHERE id = ?";
        List<UserDto> userList = jdbcTemplate.query(SELECT_SQL, (rs, rowNum) -> new UserDto(
                rs.getLong("id"),
                rs.getString("full_name"),
                rs.getString("title"),
                rs.getInt("age")), id);

        if (userList.isEmpty()) {
            return null;
        }
        log.info("User by id: {}", userList.get(0));
        return userList.get(0);
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Delete user by id: {}", id);
        jdbcTemplate.update("DELETE FROM PERSON WHERE id = ?", id);
        log.info("Deleted user with id: {}", id);
    }
}
