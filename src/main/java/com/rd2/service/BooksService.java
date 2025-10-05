package com.rd2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rd2.entity.Book;
import com.rd2.repository.BookRepository;

@Service
public class BooksService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> searchBooks(String saerchTerm) {
        if(saerchTerm == null || saerchTerm.isEmpty()) {
            throw new IllegalArgumentException("Search term must not be null or empty");
        }
        return bookRepository.searchBooks(saerchTerm);
    }
    
}
