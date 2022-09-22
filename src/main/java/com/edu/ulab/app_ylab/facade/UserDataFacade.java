package com.edu.ulab.app_ylab.facade;

import com.edu.ulab.app_ylab.dto.BookDto;
import com.edu.ulab.app_ylab.dto.UserDto;
import com.edu.ulab.app_ylab.exception.NoSuchBookException;
import com.edu.ulab.app_ylab.exception.NoSuchUserException;
import com.edu.ulab.app_ylab.exception.NotFoundException;
import com.edu.ulab.app_ylab.mapper.BookMapper;
import com.edu.ulab.app_ylab.mapper.UserMapper;
import com.edu.ulab.app_ylab.service.BookService;
import com.edu.ulab.app_ylab.service.UserService;
import com.edu.ulab.app_ylab.web.request.UserBookRequest;
import com.edu.ulab.app_ylab.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * In this class we can:
 * 1) create a user with associated books.
 * 2) edit this user with books by id:
 * - you can edit fullName, title, age for the user himself
 * - you can add new books to the user's list
 * 3) get the user and his books by id
 * 4) Remove the user along with the books. At the same time, books will also be deleted from the general list. Since this list
 * contains books with the userId of the user.
 */

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got a request to create a user with books: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest, Long userId) {
        log.info("Got a request to update a user with books: {}", userBookRequest);
        if (userService.getUserById(userId) == null) {
            throw new NoSuchUserException("There is no user with id " + userId + " in storage");
        }
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto updatedUser = userService.updateUser(userDto, userId);
        log.info("Updated user: {}", updatedUser);

        userBookRequest.getBookRequests()
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(updatedUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map((BookDto bookDto) -> bookService.updateBook(bookDto, userId)) //update
                .forEach(updateBook -> log.info("Updated book: {}", updateBook));

        List<Long> bookIdList = bookService.getBookListByUser(userId).stream()
                .filter(Objects::nonNull)
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(updatedUser.getId())
                .booksIdList(bookIdList)
                .build();

    }

    public UserBookResponse getUserWithBooks(Long userId) {
        if (userId == null) throw new NotFoundException("Id is null");
        log.info("Got a request to get a user with books: {}", userId);
        UserDto userDto = userService.getUserById(userId);
        if (userDto == null) throw new NoSuchUserException("There is no user with id " + userId + " in storage");
        List<Long> bookIdList = bookService.getBookListByUser(userId).stream()
                .filter(Objects::nonNull)
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public void deleteUserWithBooks(Long userId) {
        log.info("Got a request to delete a user with books: {}", userId);
        UserDto userDto = userService.getUserById(userId);
        List<BookDto> booksDtoByUser = bookService.getBookListByUser(userId);
        if (userDto == null) throw new NoSuchUserException("There is no user with id " + userId + " in storage");
        if (booksDtoByUser == null) throw new NoSuchBookException("No books in storage for user with id " + userId);
        userService.deleteUserById(userId);
        booksDtoByUser.forEach(bookDto -> bookService.deleteBookById(bookDto.getId()));

        log.info("User with books deleted: {}", userId);
    }
}
