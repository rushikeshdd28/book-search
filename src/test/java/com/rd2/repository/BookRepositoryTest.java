package com.rd2.repository;

import com.rd2.entity.Book;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10);

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testSearchBooks() {
        Page<Book> books = bookRepository.searchBooks("algorithms", DEFAULT_PAGEABLE);
        assertTrue(books.getContent().size() > 0);

        /* for (Book book : books) {
            System.out.println("Found book: " + book.getTitle());
        } */
    }
}
