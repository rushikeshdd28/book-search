package com.rd2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rd2.entity.Author;
import com.rd2.entity.Book;
import com.rd2.repository.BookRepository;
import com.rd2.service.AuthorService;
//import com.rd2.service.BooksService;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;
    //@Autowired
    //private BooksService bookService;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }
    
    // Get author by ID
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Integer id) {
        return authorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Search authors by name
    @GetMapping("/search")
    public ResponseEntity<List<Author>> searchAuthors(@RequestParam String name) {
        return ResponseEntity.ok(
            authorService.searchByName(name)
        );
    }
    
    // Get author with all their books
    @GetMapping("/{id}/books")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable Integer id) {
        return authorService.findById(id)
                .map(author -> ResponseEntity.ok(
                    bookRepository.findByAuthorIds(List.of(id))
                ))
                .orElse(ResponseEntity.notFound().build());
    }
    
}
