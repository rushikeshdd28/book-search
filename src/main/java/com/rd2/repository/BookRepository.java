package com.rd2.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import com.rd2.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
    
    @NonNull Page<Book> findAll(@NonNull Pageable pageable);

    Page<Book> findAllByOrderByTitle(@NonNull Pageable pageable);

    @Query(value = "SELECT DISTINCT b.* FROM Books b WHERE b.search_vector @@ to_tsquery(:searchTerm)", nativeQuery = true)
    Page<Book> searchBooks(@Param("searchTerm") String searchTerm, Pageable pageable);

    Book findByBookId(Integer bookId);

    Book findByIsbn(String isbInteger);
    
    //@Query(value = "SELECT DISTINCT b.* FROM Books b WHERE b.title LIKE %:title%", nativeQuery = true)
    Page<Book> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

    // Find books by author name (case-insensitive, partial match)
    //@Query("SELECT b FROM Books b, authors a, books_authors ba WHERE b.book_id = ba.book_id and a.author_id = ba.author_id and LOWER(a.name) LIKE LOWER(CONCAT('%', :authorName, '%'))")
    Page<Book> findByAuthorsNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
    
    @Query("SELECT DISTINCT b FROM Book b JOIN b.authors a WHERE a.authorId IN :authorIds")
    List<Book> findByAuthorIds(@Param("authorIds") List<Integer> authorIds);


}
