package com.edu.ulab.app_ylab.service.impl;

import com.edu.ulab.app_ylab.dto.BookDto;
import com.edu.ulab.app_ylab.entity.Book;
import com.edu.ulab.app_ylab.mapper.BookMapper;
import com.edu.ulab.app_ylab.repository.BookRepository;
import com.edu.ulab.app_ylab.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * This class contains the implementation of methods using Hibernate:
 * 1) createBook
 * 2) updateBook
 * 3) getBookById
 * 4) deleteBookById
 * 5) getBookListByUser
 */


@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;


    public BookServiceImpl(BookMapper bookMapper, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.bookDtoToBookEntity(bookDto);
        log.info("Mapped book: {}", book);
        Book savedBook = bookRepository.save(book);
        log.info("Saved book: {}", savedBook);
        return bookMapper.bookEntityToBookDto(savedBook);
    }

    @Override
    public BookDto updateBook(BookDto bookDto, Long userId) {
        List<BookDto> dtoList = getBookListByUser(userId);
        log.info("Get a list of BookDto: {}", dtoList);
        for (BookDto element : dtoList)
            if (element.getTitle().equals(bookDto.getTitle()) && element.getAuthor().equals(bookDto.getAuthor())) {
                return element;
            }
        return createBook(bookDto);
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findBookById(id);
        log.info("Get book by id: {}", book);
        return bookMapper.bookEntityToBookDto(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
        log.info("Deleted book with id: {}", id);
    }

    public List<BookDto> getBookListByUser(Long userId) {
        return bookRepository.findBooksByUserIdEquals(userId).stream()
                .map(bookMapper::bookEntityToBookDto)
                .toList();

    }
}
