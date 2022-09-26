package com.edu.ulab.app_ylab.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @NotEmpty(message = "Title field must not be empty in Book")
    private String title;

    @NotEmpty(message = "Author field must not be empty in Book")
    private String author;

    @Min(value = 2, message = "pageCount must be greater than 1")
    private long pageCount;
}
