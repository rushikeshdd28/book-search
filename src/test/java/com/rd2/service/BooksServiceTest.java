package com.rd2.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rd2.entity.Book;

@SpringBootTest
public class BooksServiceTest {
    @Autowired
    private BooksService booksService;

    @Test
    void testSearchBooksWhenTermIsEmpty() {
        String searchTerm = "";
        assertThrows(IllegalArgumentException.class, () -> {
            booksService.searchBooks(searchTerm);
        }); 
    }
    
    @Test
    void testSearchBooksWhenTermIsNull() {
        String searchTerm = "";
        assertThrows(IllegalArgumentException.class, () -> {
            booksService.searchBooks(searchTerm);
        }); 
    }

    @Test
    void testSearchBooksWhenTermIsValid() {
        String searchTerm = "algorithms";
        List<Book> books = booksService.searchBooks(searchTerm);
        assertTrue(books.size() > 0);
    }
}
