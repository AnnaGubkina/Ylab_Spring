package com.edu.ulab.app_ylab.mapper;

import com.edu.ulab.app_ylab.dto.BookDto;
import com.edu.ulab.app_ylab.entity.Book;
import com.edu.ulab.app_ylab.web.request.BookRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-26T14:38:24+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 18.0.2 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDto bookRequestToBookDto(BookRequest bookRequest) {
        if ( bookRequest == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        bookDto.setTitle( bookRequest.getTitle() );
        bookDto.setAuthor( bookRequest.getAuthor() );
        bookDto.setPageCount( bookRequest.getPageCount() );

        return bookDto;
    }

    @Override
    public BookRequest bookDtoToBookRequest(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        BookRequest bookRequest = new BookRequest();

        bookRequest.setTitle( bookDto.getTitle() );
        bookRequest.setAuthor( bookDto.getAuthor() );
        bookRequest.setPageCount( bookDto.getPageCount() );

        return bookRequest;
    }

    @Override
    public Book bookDtoToBookEntity(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        Book book = new Book();

        book.setId( bookDto.getId() );
        book.setUserId( bookDto.getUserId() );
        book.setTitle( bookDto.getTitle() );
        book.setAuthor( bookDto.getAuthor() );
        book.setPageCount( bookDto.getPageCount() );

        return book;
    }

    @Override
    public BookDto bookEntityToBookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        bookDto.setId( book.getId() );
        bookDto.setUserId( book.getUserId() );
        bookDto.setTitle( book.getTitle() );
        bookDto.setAuthor( book.getAuthor() );
        bookDto.setPageCount( book.getPageCount() );

        return bookDto;
    }
}
