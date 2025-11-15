package com.rd2.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.rd2.entity.Book;

@SpringBootTest
public class BooksServiceTest {
    @Autowired
    private BooksService booksService;

    private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10);

    @Test
    void testSearchBooksWhenTermIsEmpty() {
        String searchTerm = "";
        assertThrows(IllegalArgumentException.class, () -> {
            booksService.searchBooks(searchTerm, DEFAULT_PAGEABLE);
        }); 
    }
    
    @Test
    void testSearchBooksWhenTermIsNull() {
        String searchTerm = "";
        assertThrows(IllegalArgumentException.class, () -> {
            booksService.searchBooks(searchTerm, DEFAULT_PAGEABLE);
        }); 
    }

    @Test
    void testSearchBooksWhenTermIsValid() {
        String searchTerm = "algorithms";
        Page<Book> books = booksService.searchBooks(searchTerm, DEFAULT_PAGEABLE);
        assertTrue(books.getContent().size() > 0);
    }
}
