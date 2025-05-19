package com.example.ReactiveProgramming.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "book_id")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @Column("book_id")
    private  Integer bookId;

    private  String name;

    @Column("book_desc")
    private  String description;

    private  String publisher;

    private  String author;
}
