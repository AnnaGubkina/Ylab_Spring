package com.edu.ulab.app_ylab.repository;

import com.edu.ulab.app_ylab.dto.BookDto;
import com.edu.ulab.app_ylab.entity.Book;
import io.micrometer.core.instrument.Tags;

import java.util.List;

public interface BookRepository {

    void saveBook(Book book);

    Book getBookById(Long id);

    List<Book> getBookListByUser(Long userId);

    void deleteBookById(Long id);
}
