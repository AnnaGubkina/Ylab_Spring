package com.edu.ulab.app_ylab.mapper;

import com.edu.ulab.app_ylab.dto.BookDto;
import com.edu.ulab.app_ylab.dto.UserDto;
import com.edu.ulab.app_ylab.entity.Book;
import com.edu.ulab.app_ylab.entity.User;
import com.edu.ulab.app_ylab.web.request.BookRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto bookRequestToBookDto(BookRequest bookRequest);

    BookRequest bookDtoToBookRequest(BookDto bookDto);

    Book bookDtoToBookEntity(BookDto bookDto);

    BookDto bookEntityToBookDto(Book book);
}
