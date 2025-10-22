package com.rd2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rd2.dto.BookAuthorsDTO;
import com.rd2.dto.BookDTO;
import com.rd2.dto.BookPatchDTO;
import com.rd2.entity.Book;
import com.rd2.service.BooksService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BooksService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Book> searchBooksById(@PathVariable Integer id) {
        if(id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book book = bookService.searchBooksById(id);
        if(book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String searchTerm) {
        return bookService.searchBooks(searchTerm);
    }

     // Search books by title
    @GetMapping("/title")
    public List<Book> searchBooksByTitle(@RequestParam String title) {
        return bookService.searchBooksByTitle(title);
    }
    
    // Get books by author
    @GetMapping("/author")
    public List<Book> getBooksByAuthor(@RequestParam String name) {
        return bookService.getBooksByAuthor(name);
    }
    
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDto) {
        System.out.println("authorNames = "+bookDto.getAuthors());
        //return new ResponseEntity<>(null);
        return new ResponseEntity<Book>(bookService.createBookWithAuthors(bookDto.getBook(),  bookDto.getAuthors()), HttpStatus.CREATED);
    }

    @PostMapping("/with-authors")
    public ResponseEntity<Book> createBook(@RequestBody BookAuthorsDTO bookAuthorDto) {
        System.out.println("authorNames = "+bookAuthorDto.getAuthor());
        Book book = new Book();
        book.setTitle(bookAuthorDto.getTitle());
        book.setRating(bookAuthorDto.getRating());
        book.setDescription(bookAuthorDto.getDescription());
        book.setLanguage(bookAuthorDto.getLanguage());
        book.setIsbn(bookAuthorDto.getIsbn());
        book.setBookFormat(bookAuthorDto.getBookFormat());
        book.setEdition(bookAuthorDto.getEdition());
        book.setPages(bookAuthorDto.getPages());
        book.setPublisher(bookAuthorDto.getPublisher());
        book.setFirstPublishDate(bookAuthorDto.getFirstPublishDate());
        book.setLikedPercent(bookAuthorDto.getLikedPercent());
        book.setPrice(bookAuthorDto.getPrice());

        return new ResponseEntity<>(bookService.createBookWithAuthors(book,  bookAuthorDto.getAuthor()), HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable Integer id) {
        if(id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book updatedBook = bookService.updateBook(id, book);
        if(book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Book>(updatedBook, HttpStatus.OK);
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<Book> partialUpdate(
            @PathVariable Integer id,
            @Valid @RequestBody BookPatchDTO patchDTO) {
        
        Book updatedBook = bookService.partialUpdate(id, patchDTO);
        
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        if(id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
