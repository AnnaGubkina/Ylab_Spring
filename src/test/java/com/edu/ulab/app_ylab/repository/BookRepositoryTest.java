package com.edu.ulab.app_ylab.repository;


import com.edu.ulab.app_ylab.config.SystemJpaTest;
import com.edu.ulab.app_ylab.entity.Book;
import com.edu.ulab.app_ylab.entity.Person;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.vladmihalcea.sql.SQLStatementCountValidator.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты репозитория {@link BookRepository}.
 * Количество селектов в assertSelectCount() зависит от того как мы запускаем тесты.
 * Если запускаем сразу весь класс со всеми тестами, то select из sequence выполнится только на тесте, который запустится первым.
 * Если запускать каждый тест по отдельности , то необходимо в  assertSelectCount() дописать количество select из sequence
 * В данном случае я написала вариант - запускать сразу целиком весь класс.
 */
@SystemJpaTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        SQLStatementCountValidator.reset();
    }

    @DisplayName("Сохранить книгу(user_id берем из person). Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void saveBook_thenAssertDmlCount() {
        //Given

        Person person = new Person();
        person.setFullName("Test Test");
        person.setTitle("reader");
        person.setAge(105);

        Person savedPerson = userRepository.save(person);

        Book book = new Book();
        book.setUserId(savedPerson.getId());
        book.setTitle("test");
        book.setAuthor("Test Author");
        book.setPageCount(1000);


        //When
        Book result = bookRepository.save(book);

        //Then
        assertThat(result.getPageCount()).isEqualTo(1000);
        assertThat(result.getTitle()).isEqualTo("test");
        assertSelectCount(0);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    @DisplayName("Изменить книгу(user_id берем из person). Число select должно равняться 3, insert 2")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void updateBook_thenAssertDmlCount() {
        //Given

        Person person = new Person();
        person.setFullName("Test Test");
        person.setTitle("readerr");
        person.setAge(66);

        Person savedPerson = userRepository.save(person);

        Book updateBook = new Book();
        updateBook.setUserId(savedPerson.getId());
        updateBook.setTitle("test updateBook");
        updateBook.setAuthor("Test Author");
        updateBook.setPageCount(350);


        //When
        Book bookForUpdate = bookRepository.save(updateBook);
        Book updatedBook = bookRepository.findBookById(bookForUpdate.getId());


        //Then
        assertThat(bookForUpdate.getPageCount()).isEqualTo(350);
        assertThat(bookForUpdate.getUserId()).isEqualTo(savedPerson.getId());
        assertThat(updatedBook.getTitle()).isEqualTo("test updateBook");
        assertThat(updatedBook.getUserId()).isEqualTo(savedPerson.getId());
        assertThat(updatedBook.getPageCount()).isEqualTo(350);
        assertSelectCount(3);
        assertInsertCount(2);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    @DisplayName("Получить книгу. Число select должно равняться 2, insert 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getBook_thenAssertDmlCount() {
        //Given
        Book book = new Book();
        book.setUserId(300L);
        book.setTitle("test Book");
        book.setAuthor("Test Author");
        book.setPageCount(560);

        //When
        Book savedBook = bookRepository.save(book);
        Book bookFromBD = bookRepository.findBookById(savedBook.getId());

        //Then
        assertThat(savedBook.getPageCount()).isEqualTo(560);
        assertThat(bookFromBD.getTitle()).isEqualTo("test Book");
        assertThat(bookFromBD.getUserId()).isEqualTo(300L);
        assertThat(bookFromBD.getPageCount()).isEqualTo(560);
        assertSelectCount(1);
        assertInsertCount(1);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }


    @DisplayName("Получаем все книги пользвателя. Число select должно равняться 3, insert 3")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getAllBooks_thenAssertDmlCount() {
        //Given
        Person person = new Person();
        person.setFullName("Test Person");
        person.setTitle("reade_r");
        person.setAge(31);

        Person savedPerson = userRepository.save(person);

        Book book = new Book();
        book.setUserId(savedPerson.getId());
        book.setTitle("book_book");
        book.setAuthor("Test Author");
        book.setPageCount(1500);

        Book book2 = new Book();
        book2.setUserId(savedPerson.getId());
        book2.setTitle("book_2");
        book2.setAuthor("Test Author 2");
        book2.setPageCount(410);

        //When
        Book savedBook = bookRepository.save(book);
        Book savedBook2 = bookRepository.save(book2);
        List<Book> bookListByUser = bookRepository.findBooksByUserIdEquals(savedPerson.getId());

        //todo подумать над листом(сортировать или нет? ) метод может упасть, почитать
        //Then
        assertThat(savedBook.getPageCount()).isEqualTo(1500);
        assertThat(savedBook2.getPageCount()).isEqualTo(410);
        assertThat(bookListByUser.get(0).getTitle()).isEqualTo("book_book");
        assertThat(bookListByUser.get(1).getTitle()).isEqualTo("book_2");
        assertSelectCount(1);
        assertInsertCount(3);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    @DisplayName("Удаляем книгу. ЧЧисло select должно равняться 2, insert 1, delete 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void deleteBook_thenAssertDmlCount() {
        //Given
        Book book = new Book();
        book.setUserId(375L);
        book.setTitle("book_delBook");
        book.setAuthor("Test Author");
        book.setPageCount(700);

        //When
        Book savedBook = bookRepository.save(book);
        bookRepository.deleteById(savedBook.getId());
        Book deletedBook = bookRepository.findBookById(savedBook.getId());


        //Then
        assertThat(savedBook.getPageCount()).isEqualTo(700);
        assertThat(deletedBook).isNull();
        assertSelectCount(1);
        assertInsertCount(1);
        assertUpdateCount(0);
        assertDeleteCount(1);
    }

}
