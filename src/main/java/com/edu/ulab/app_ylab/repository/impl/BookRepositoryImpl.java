package com.edu.ulab.app_ylab.repository.impl;

import com.edu.ulab.app_ylab.dto.BookDto;
import com.edu.ulab.app_ylab.entity.Book;
import com.edu.ulab.app_ylab.repository.BookRepository;
import com.edu.ulab.app_ylab.storage.Storage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private Storage storage;

    public BookRepositoryImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void saveBook(Book book) {
        storage.saveBook(book);
    }

    @Override
    public Book getBookById(Long id) {
        return storage.getBook(id);
    }

    @Override
    public List<Book> getBookListByUser(Long userId) {
        return storage.getBookListByUser(userId);
    }

    @Override
    public void deleteBookById(Long id) {
        storage.removeBook(id);
    }
}
