package com.edu.ulab.app_ylab.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String fullName;
    private String title;
    private int age;
    private List<Book> bookList;

    public void addBooksToUser(Book book) {
        if (bookList == null) {
            bookList = new ArrayList<>();
        }
        bookList.add(book);
    }
}
