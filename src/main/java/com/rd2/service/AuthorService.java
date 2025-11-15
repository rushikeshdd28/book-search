package com.rd2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rd2.entity.Author;
import com.rd2.entity.Book;
import com.rd2.repository.AuthorRepository;
import com.rd2.repository.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;
    
    public Optional<Author> findById(Integer id) {
        Optional<Author> author = authorRepository.findById(id);
        return author;
                //.orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }
    // Get all authors
    @Transactional(readOnly = true)
    public Page<Author> getAllAuthors(Pageable pageable) {
        log.info("Fetching all authors");
        return authorRepository.findAll(pageable);
    }
    
    // Search authors by name
    @Transactional(readOnly = true)
    public Page<Author> searchByName(String name, Pageable pageable) {
        log.info("Searching authors by name: {}", name);
        return authorRepository.findByNameContainingIgnoreCase(name, pageable);
    }
    
    // Get author by exact name
    @Transactional(readOnly = true)
    public Optional<Author> getByName(String name) {
        log.info("Getting author by exact name: {}", name);
        return authorRepository.findByName(name);
    }

    public List<Book> findByAuthorIds(List<Integer> ids) {

        return null;
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }
    
    @Transactional
    public Author deleteAuthor(Author author) {
        if(author == null) 
        {
            throw new RuntimeException("Author not found");
        }
        authorRepository.delete(author);
        return author;
    }
}
