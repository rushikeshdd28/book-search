package com.rd2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rd2.dto.BookPatchDTO;
import com.rd2.entity.Author;
import com.rd2.entity.Book;
import com.rd2.exception.ResourceNotFoundException;
import com.rd2.repository.AuthorRepository;
import com.rd2.repository.BookRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BooksService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        log.info("Fetching all authors");
        return bookRepository.findAll();
    }

     // Create a new book
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    // Create book with authors (by author names)
    public Book createBookWithAuthors(Book book, List<Author> authorNames) {
        for (Author authorName : authorNames) {
            Author author = authorRepository.findByName(authorName.getName())
                    .orElseGet(() -> {
                        Author newAuthor = new Author();
                        newAuthor.setName(authorName.getName());
                        return authorRepository.save(newAuthor);
                    });
            book.addAuthor(author);
        }
        return bookRepository.save(book);
    }

    public Book createBookWithAuthors(Book book, String[] authorNames) {
        for (String authorName : authorNames) {
            Author author = authorRepository.findByName(authorName)
                    .orElseGet(() -> {
                        Author newAuthor = new Author();
                        newAuthor.setName(authorName);
                        return authorRepository.save(newAuthor);
                    });
            book.addAuthor(author);
        }
        return bookRepository.save(book);
    }

    public List<Book> searchBooks(String saerchTerm) {
        if(saerchTerm == null || saerchTerm.isEmpty()) {
            throw new IllegalArgumentException("Search term must not be null or empty");
        }
        return bookRepository.searchBooks(saerchTerm);
    }

    public Book searchBooksById(Integer id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("id must not be null or less than or equal to zero");
        }
        return bookRepository.findByBookId(id);
    }
    
    // Search books by title
    @Transactional(readOnly = true)
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    // Get books by author name
    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(String name) {
        return bookRepository.findByAuthorsNameContainingIgnoreCase(name);
    }

    // Add author to existing book
    public Book addAuthorToBook(Integer bookId, Integer authorId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        
        book.addAuthor(author);
        return bookRepository.save(book);
    }
    
    // Remove author from book
    public Book removeAuthorFromBook(Integer bookId, Integer authorId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        
        book.removeAuthor(author);
        return bookRepository.save(book);
    }
    
    // Update book
    public Book updateBook(Integer id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        book.setTitle(bookDetails.getTitle());
        book.setRating(bookDetails.getRating());
        book.setDescription(bookDetails.getDescription());
        book.setLanguage(bookDetails.getLanguage());
        book.setIsbn(bookDetails.getIsbn());
        book.setBookFormat(bookDetails.getBookFormat());
        book.setEdition(bookDetails.getEdition());
        book.setPages(bookDetails.getPages());
        book.setPublisher(bookDetails.getPublisher());
        book.setPublishDate(bookDetails.getPublishDate());
        book.setFirstPublishDate(bookDetails.getFirstPublishDate());
        book.setLikedPercent(bookDetails.getLikedPercent());
        book.setPrice(bookDetails.getPrice());
        
        return bookRepository.save(book);
    }

    @Transactional
    public Book partialUpdate(Integer id, BookPatchDTO patchDTO) {
        log.info("Partial update for book ID: {}", id);
        
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        
        // Update only non-null fields
        patchDTO.getTitle().ifPresent(book::setTitle);
        
        patchDTO.getRating().ifPresent(book::setRating);
        
        patchDTO.getDescription().ifPresent(book::setDescription);
        
        patchDTO.getLanguage().ifPresent(book::setLanguage);
        
        patchDTO.getIsbn().ifPresent(book::setIsbn);
        
        patchDTO.getBookFormat().ifPresent(book::setBookFormat);
        
        patchDTO.getEdition().ifPresent(book::setEdition);
        
        patchDTO.getPages().ifPresent(book::setPages);
        
        patchDTO.getPublisher().ifPresent(book::setPublisher);
        
        patchDTO.getPublishDate().ifPresent(book::setPublishDate);
        
        patchDTO.getFirstPublishDate().ifPresent(book::setFirstPublishDate);
        
        patchDTO.getLikedPercent().ifPresent(book::setLikedPercent);
        
        patchDTO.getPrice().ifPresent(book::setPrice);
        
        // Save will trigger @DynamicUpdate
        // PostgreSQL trigger will auto-update search_vector
        Book savedBook = bookRepository.save(book);
        
        log.info("Book updated successfully: {}", savedBook.getBookId());
        return savedBook;
    }

    
    // Delete book
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }
}
