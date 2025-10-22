package com.rd2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rd2.entity.Author;
import com.rd2.entity.Book;
import com.rd2.repository.AuthorRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    
    public Optional<Author> findById(Integer id) {
        return authorRepository.findById(id);
                //.orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }
    // Get all authors
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        log.info("Fetching all authors");
        return authorRepository.findAll();
    }
    
    // Search authors by name
    @Transactional(readOnly = true)
    public List<Author> searchByName(String name) {
        log.info("Searching authors by name: {}", name);
        return authorRepository.findByNameContainingIgnoreCase(name);
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
}
