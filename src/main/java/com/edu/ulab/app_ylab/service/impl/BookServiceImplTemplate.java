package com.edu.ulab.app_ylab.service.impl;

import com.edu.ulab.app_ylab.dto.BookDto;
import com.edu.ulab.app_ylab.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * This class contains the implementation of methods using JdbcTemplate :
 * 1) createBook
 * 2) updateBook
 * 3) getBookById
 * 4) deleteBookById
 * 5) getBookListByUser
 */

@Slf4j
@Service
public class BookServiceImplTemplate implements BookService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookServiceImplTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        log.info("Create book in database: {}", bookDto);
        final String INSERT_SQL = "INSERT INTO BOOK(TITLE, AUTHOR, PAGE_COUNT, USER_ID) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                        ps.setString(1, bookDto.getTitle());
                        ps.setString(2, bookDto.getAuthor());
                        ps.setLong(3, bookDto.getPageCount());
                        ps.setLong(4, bookDto.getUserId());
                        return ps;
                    }
                },
                keyHolder);
        bookDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.info("Created book: {}", bookDto);
        return bookDto;

    }

    @Override
    public BookDto updateBook(BookDto bookDto, Long userId) {
        List<BookDto> dtoList = getBookListByUser(userId);
        log.info("List of BookDto: {}", dtoList);
        for (BookDto element : dtoList)
            if (element.getTitle().equals(bookDto.getTitle()) && element.getAuthor().equals(bookDto.getAuthor())) {
                log.info("Return equal book: {}", element);
                return element;
            }
        return createBook(bookDto);
    }

    @Override
    public BookDto getBookById(Long id) {
        final String SELECT_SQL = "SELECT * FROM BOOK WHERE id = ?";
        List<BookDto> bookList = jdbcTemplate.query(SELECT_SQL, (rs, rowNum) -> new BookDto(rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getLong("page_count")), id);
        log.info("Get a list of BookDto: {}", bookList);
        if (bookList.isEmpty()) {
            return null;
        }
        log.info("Book by id: {}", bookList.get(0));
        return bookList.get(0);
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("DELETE FROM BOOK WHERE id = ?", id);
        log.info("Deleted book with id: {}", id);
    }

    @Override
    public List<BookDto> getBookListByUser(Long userId) {
        final String SELECT_SQL = "SELECT * FROM BOOK WHERE user_id = ?";
        return jdbcTemplate.query(SELECT_SQL, new BeanPropertyRowMapper<>(BookDto.class), userId);
    }
}

