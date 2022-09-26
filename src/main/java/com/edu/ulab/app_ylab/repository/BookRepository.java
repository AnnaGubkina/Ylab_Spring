package com.edu.ulab.app_ylab.repository;

import com.edu.ulab.app_ylab.entity.Book;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.List;


public interface BookRepository extends CrudRepository<Book, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Book findBookById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Book> findBooksByUserIdEquals(Long userId);

}
