package com.rd2.dto;

import java.util.List;

import com.rd2.entity.Author;
import com.rd2.entity.Book;

public class BookDTO {
    Book book;
    private List<Author> authors; 

    public BookDTO() {
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    
}
