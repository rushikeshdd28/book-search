package com.rd2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.rd2.entity.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer>, CrudRepository<Book, Integer> {

    List<Book> findAll();

    @Query(value = "SELECT DISTINCT b.* FROM Books b WHERE b.search_vector @@ to_tsquery(:searchTerm)", nativeQuery = true)
    List<Book> searchBooks(@Param("searchTerm") String searchTerm);

    Book findByBookId(Integer bookId);
    
    //@Query(value = "SELECT DISTINCT b.* FROM Books b WHERE b.title LIKE %:title%", nativeQuery = true)
    List<Book> findByTitleContainingIgnoreCase(@Param("title") String title);

    // Find books by author name (case-insensitive, partial match)
    //@Query("SELECT b FROM Books b, authors a, books_authors ba WHERE b.book_id = ba.book_id and a.author_id = ba.author_id and LOWER(a.name) LIKE LOWER(CONCAT('%', :authorName, '%'))")
    List<Book> findByAuthorsNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT DISTINCT b FROM Book b JOIN b.authors a WHERE a.authorId IN :authorIds")
    List<Book> findByAuthorIds(@Param("authorIds") List<Integer> authorIds);


}
