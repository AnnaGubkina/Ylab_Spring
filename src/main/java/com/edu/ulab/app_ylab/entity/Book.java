package com.edu.ulab.app_ylab.entity;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private Long id;
    private Long userId;
    private String title;
    private String author;
    private long pageCount;
}
