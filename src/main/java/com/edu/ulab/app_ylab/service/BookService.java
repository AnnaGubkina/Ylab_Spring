package com.edu.ulab.app_ylab.service;


import com.edu.ulab.app_ylab.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto userDto);

    BookDto updateBook(BookDto bookDto, Long userId);

    BookDto getBookById(Long id);

    void deleteBookById(Long id);

    List<BookDto> getBookListByUser (Long userId);
}
