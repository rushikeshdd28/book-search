package com.rd2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rd2.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    
     // Find author by name (exact match)
    Optional<Author> findByName(String name);
    
    List<Author> findAll();

    // Find authors by name (case-insensitive, partial match)
    List<Author> findByNameContainingIgnoreCase(String name);
    
    // Find authors who have written a specific book
    @Query("SELECT a FROM Author a JOIN a.books b WHERE b.bookId = :bookId")
    List<Author> findByBookId(@Param("bookId") Long bookId);
    
    // Get all authors with their books (fetch join)
    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN FETCH a.books")
    List<Author> findAllWithBooks();
} 