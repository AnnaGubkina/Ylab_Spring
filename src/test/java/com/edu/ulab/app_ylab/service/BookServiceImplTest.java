package com.edu.ulab.app_ylab.service;


import com.edu.ulab.app_ylab.dto.BookDto;
import com.edu.ulab.app_ylab.entity.Book;
import com.edu.ulab.app_ylab.exception.NoSuchBookException;
import com.edu.ulab.app_ylab.mapper.BookMapper;
import com.edu.ulab.app_ylab.repository.BookRepository;
import com.edu.ulab.app_ylab.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * Тестирование функционала {@link com.edu.ulab.app_ylab.service.impl.BookServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing book functionality.")
public class BookServiceImplTest {
    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookMapper bookMapper;

    @Test
    @DisplayName("Создание книги. Должно пройти успешно.")
    void saveBook_Test() {
        //given

        BookDto bookDto = new BookDto();
        bookDto.setUserId(1L);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        BookDto result = new BookDto();
        result.setId(20L);
        result.setUserId(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book book = new Book();
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setUserId(1L);

        Book savedBook = new Book();
        savedBook.setId(20L);
        savedBook.setUserId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");

        //when
        when(bookMapper.bookDtoToBookEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.bookEntityToBookDto(savedBook)).thenReturn(result);


        //then
        BookDto bookDtoResult = bookService.createBook(bookDto);

        assertEquals(20L, bookDtoResult.getId());
        assertEquals("test title", bookDtoResult.getTitle());
        assertEquals("test author", bookDtoResult.getAuthor());
        assertEquals(1000, bookDtoResult.getPageCount());
    }


    /**
     * В тестируемом методе updateBook(BookDto bookDto, Long userId) из класса BookServiceImpl , существующие у
     * пользователя книги не изменяются.
     * Логика данного метода заключается в том, чтобы добавить новые книги пользователю(книги с одинаковым user_id),
     * если таких книг у него еще нет. А если есть то метод возвращает имеющуюся книгу.
     * Поэтому на данный метод будет 2 теста с двумя сценариями.
     */

    @Test
    @DisplayName("Изменение книги. Сценарий 1 - если такой книги в списке пользователя нет. Должно пройти успешно.")
    void updateBook_Test() {
        //given

        Long userId = 400L;

        //первая книга
        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setUserId(userId);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setUserId(userId);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        //вторая книга

        Book savedBook2 = new Book();
        savedBook2.setId(2L);
        savedBook2.setUserId(userId);
        savedBook2.setPageCount(450);
        savedBook2.setTitle("test book");
        savedBook2.setAuthor("test author2");

        BookDto bookDto2 = new BookDto();
        bookDto2.setId(2L);
        bookDto2.setUserId(userId);
        bookDto2.setPageCount(450);
        bookDto2.setTitle("test book");
        bookDto2.setAuthor("test author2");

        List<Book> bookListByUser = new ArrayList<>();
        bookListByUser.add(savedBook);
        bookListByUser.add(savedBook2);

        //новая книга
        BookDto newBookDtoToAdd = new BookDto();
        newBookDtoToAdd.setUserId(userId);
        newBookDtoToAdd.setAuthor("test NewAuthor");
        newBookDtoToAdd.setTitle("test NewTitle");
        newBookDtoToAdd.setPageCount(500);

        Book newBookToAdd = new Book();
        newBookToAdd.setUserId(userId);
        newBookToAdd.setAuthor("test NewAuthor");
        newBookToAdd.setTitle("test NewTitle");
        newBookToAdd.setPageCount(500);

        Book savedBookToAdd = new Book();
        savedBookToAdd.setId(3L);
        savedBookToAdd.setUserId(userId);
        savedBookToAdd.setAuthor("test NewAuthor");
        savedBookToAdd.setTitle("test NewTitle");
        savedBookToAdd.setPageCount(500);

        BookDto savedNewBookDto = new BookDto();
        savedNewBookDto.setId(3L);
        savedNewBookDto.setUserId(userId);
        savedNewBookDto.setAuthor("test NewAuthor");
        savedNewBookDto.setTitle("test NewTitle");
        savedNewBookDto.setPageCount(500);


        List<Book> updatedBookListByUser = new ArrayList<>();
        updatedBookListByUser.add(savedBook);
        updatedBookListByUser.add(savedBook2);
        updatedBookListByUser.add(savedBookToAdd);


        //when
        when(bookRepository.findBooksByUserIdEquals(userId)).thenReturn(bookListByUser);
        when(bookMapper.bookEntityToBookDto(savedBook)).thenReturn(bookDto);
        when(bookMapper.bookEntityToBookDto(savedBook2)).thenReturn(bookDto2);
        when(bookMapper.bookDtoToBookEntity(newBookDtoToAdd)).thenReturn(newBookToAdd);
        when(bookRepository.save(newBookToAdd)).thenReturn(savedBookToAdd);
        when(bookMapper.bookEntityToBookDto(savedBookToAdd)).thenReturn(savedNewBookDto);

        when(bookRepository.findBooksByUserIdEquals(userId)).thenReturn(updatedBookListByUser);
        when(bookMapper.bookEntityToBookDto(savedBook)).thenReturn(bookDto);
        when(bookMapper.bookEntityToBookDto(savedBook2)).thenReturn(bookDto2);
        when(bookMapper.bookEntityToBookDto(savedBookToAdd)).thenReturn(savedNewBookDto);


        //then
        BookDto bookDtoResult = bookService.updateBook(newBookDtoToAdd, userId);
        List<BookDto> updatedDtoListByUserResult = bookService.getBookListByUser(userId);

        assertEquals(3L, bookDtoResult.getId());
        assertEquals("test NewTitle", bookDtoResult.getTitle());
        assertEquals("test NewAuthor", bookDtoResult.getAuthor());
        assertEquals(500, bookDtoResult.getPageCount());

        assertEquals(bookDtoResult, updatedDtoListByUserResult.get(2));

    }


    @Test
    @DisplayName("Изменение книги. Сценарий 2 - если такая книга в списке пользователя есть. Должно пройти успешно.")
    void updateBookIfBookExist_Test() {
        //given

        Long userId = 400L;

        //первая книга
        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setUserId(userId);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setUserId(userId);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        //вторая книга

        Book savedBook2 = new Book();
        savedBook2.setId(2L);
        savedBook2.setUserId(userId);
        savedBook2.setPageCount(450);
        savedBook2.setTitle("test book");
        savedBook2.setAuthor("test author2");

        BookDto bookDto2 = new BookDto();
        bookDto2.setId(2L);
        bookDto2.setUserId(userId);
        bookDto2.setPageCount(450);
        bookDto2.setTitle("test book");
        bookDto2.setAuthor("test author2");

        List<Book> bookListByUser = new ArrayList<>();
        bookListByUser.add(savedBook);
        bookListByUser.add(savedBook2);

        //новая книга
        BookDto newBookDtoToAdd = new BookDto();
        newBookDtoToAdd.setUserId(userId);
        newBookDtoToAdd.setAuthor("test author");
        newBookDtoToAdd.setTitle("test title");
        newBookDtoToAdd.setPageCount(1000);

        Book newBookToAdd = new Book();
        newBookToAdd.setUserId(userId);
        newBookToAdd.setAuthor("test author");
        newBookToAdd.setTitle("test title");
        newBookToAdd.setPageCount(1000);

        List<Book> updatedBookListByUser = new ArrayList<>();
        updatedBookListByUser.add(savedBook);
        updatedBookListByUser.add(savedBook2);

        BookDto returnedBook = new BookDto();
        if (bookDto.getTitle().equals(newBookDtoToAdd.getTitle()) && bookDto.getAuthor().equals(newBookDtoToAdd.getAuthor())) {
            returnedBook = bookDto;
        }


        //when
        when(bookRepository.findBooksByUserIdEquals(userId)).thenReturn(bookListByUser);
        when(bookMapper.bookEntityToBookDto(savedBook)).thenReturn(bookDto);
        when(bookMapper.bookEntityToBookDto(savedBook2)).thenReturn(bookDto2);

        when(bookRepository.findBooksByUserIdEquals(userId)).thenReturn(updatedBookListByUser);
        when(bookMapper.bookEntityToBookDto(savedBook)).thenReturn(bookDto);
        when(bookMapper.bookEntityToBookDto(savedBook2)).thenReturn(bookDto2);


        //then
        BookDto bookDtoResult = bookService.updateBook(newBookDtoToAdd, userId);
        List<BookDto> updatedDtoListByUserResult = bookService.getBookListByUser(userId);

        assertEquals(returnedBook.getId(), bookDtoResult.getId());
        assertEquals(returnedBook.getTitle(), bookDtoResult.getTitle());
        assertEquals(returnedBook.getAuthor(), bookDtoResult.getAuthor());
        assertEquals(returnedBook.getPageCount(), bookDtoResult.getPageCount());

        assertEquals(bookDtoResult, updatedDtoListByUserResult.get(0));

    }


    @Test
    @DisplayName("Получение книги. Должно пройти успешно.")
    void getBook_Test() {
        //given

        Long id = 30L;
        Long userId = 100L;

        Book savedBook = new Book();
        savedBook.setId(id);
        savedBook.setUserId(userId);
        savedBook.setPageCount(300);
        savedBook.setTitle("test get");
        savedBook.setAuthor("test getAuthor");

        BookDto result = new BookDto();
        result.setId(id);
        result.setUserId(userId);
        result.setAuthor("test getAuthor");
        result.setTitle("test get");
        result.setPageCount(300);

        //when
        when(bookRepository.findBookById(id)).thenReturn(savedBook);
        when(bookMapper.bookEntityToBookDto(savedBook)).thenReturn(result);


        //then
        BookDto bookDtoResult = bookService.getBookById(id);
        assertEquals(30L, bookDtoResult.getId());
        assertEquals("test get", bookDtoResult.getTitle());
        assertEquals("test getAuthor", bookDtoResult.getAuthor());
        assertEquals(300, bookDtoResult.getPageCount());
    }


    @Test
    @DisplayName("Получение списка книг пользователя. Должно пройти успешно.")
    void getAllBookByUser_Test() {
        //given

        //первая книга
        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setUserId(400L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");

        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setUserId(400L);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        //вторая книга
        Book savedBook2 = new Book();
        savedBook2.setId(2L);
        savedBook2.setUserId(400L);
        savedBook2.setPageCount(450);
        savedBook2.setTitle("test all");
        savedBook2.setAuthor("test author2");

        BookDto bookDto2 = new BookDto();
        bookDto2.setId(2L);
        bookDto2.setUserId(400L);
        bookDto2.setPageCount(450);
        bookDto2.setTitle("test all");
        bookDto2.setAuthor("test author2");

        List<Book> bookListByUser = new ArrayList<>();
        bookListByUser.add(savedBook);
        bookListByUser.add(savedBook2);


        //when
        when(bookRepository.findBooksByUserIdEquals(400L)).thenReturn(bookListByUser);
        when(bookMapper.bookEntityToBookDto(savedBook)).thenReturn(bookDto);
        when(bookMapper.bookEntityToBookDto(savedBook2)).thenReturn(bookDto2);

        //then
        List<BookDto> bookDtoListByUserResult = bookService.getBookListByUser(400L);

        assertEquals(bookDto, bookDtoListByUserResult.get(0));
        assertEquals(bookDto2, bookDtoListByUserResult.get(1));
    }

    @Test
    @DisplayName("Удаление книги. Должно пройти успешно.")
    void deleteBook_Test() {
        //given

        Long id = 50L;
        Long userId = 700L;

        Book savedBook = new Book();
        savedBook.setId(id);
        savedBook.setUserId(userId);
        savedBook.setPageCount(300);
        savedBook.setTitle("test delete");
        savedBook.setAuthor("test Author");

        //when
        when(bookRepository.findBookById(id)).thenReturn(savedBook);
        doNothing().when(bookRepository).deleteById(id);

        //then
        BookDto bookDtoResult = bookService.getBookById(id);
        assertNull(bookDtoResult);
    }


    /**
     * Failed tests
     */

    @Test
    @DisplayName("Получение книги, которой нет в БД.")
    void getErrorBook_Test() {

        //Given
        Long id = 500L;

        //When
        doThrow(new NoSuchBookException("There is no book with id " + id + " in database"))
                .when(bookRepository).findBookById(id);

        //Then
        assertThatThrownBy(() -> bookService.getBookById(id))
                .isInstanceOf(NoSuchBookException.class)
                .hasMessage("There is no book with id " + id + " in database");


    }

    @Test
    @DisplayName("Удаление книги, которой нет в БД.")
    void deleteErrorBook_Test() {

        //Given
        Long id = 300L;

        //When
        doThrow(new NoSuchBookException("There is no book with id " + id + " in database"))
                .when(bookRepository).deleteById(id);

        //Then
        assertThatThrownBy(() -> bookService.deleteBookById(id))
                .isInstanceOf(NoSuchBookException.class)
                .hasMessage("There is no book with id " + id + " in database");

    }
}