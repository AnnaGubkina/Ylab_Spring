package com.edu.ulab.app_ylab.service.impl;

import com.edu.ulab.app_ylab.dto.BookDto;
import com.edu.ulab.app_ylab.entity.Book;
import com.edu.ulab.app_ylab.mapper.BookMapper;
import com.edu.ulab.app_ylab.repository.BookRepository;
import com.edu.ulab.app_ylab.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public static Long BOOK_ID_GENERATOR = 0L;

    public BookServiceImpl(BookMapper bookMapper, BookRepository bookRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Long id = ++BOOK_ID_GENERATOR;
        Book book = bookMapper.bookDtoToBookEntity(bookDto);
        book.setId(id);
        bookRepository.saveBook(book);
        log.info("Created book: {}", book);
        return getBookById(book.getId());
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
        Book book = bookRepository.getBookById(id);
        log.info("Get book by id: {}", book);
        return bookMapper.bookEntityToBookDto(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteBookById(id);
        log.info("Book deleted: {}", id);

    }

    public List<BookDto> getBookListByUser(Long userId) {
        return bookRepository.getBookListByUser(userId).stream()
                .map(bookMapper::bookEntityToBookDto)
                .toList();
    }
}
