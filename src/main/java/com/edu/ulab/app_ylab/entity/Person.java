package com.edu.ulab.app_ylab.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 30, message = "Full name should be between 2 and 30 characters")
    private String fullName;

    @NotEmpty(message = "Please fill in the title")
    private String title;

    @Min(value = 1, message = "age must be greater than 0")
    @Max(value = 110, message = "age must be less than 111")
    private int age;

}
