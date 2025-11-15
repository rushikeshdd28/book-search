package com.rd2.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rd2.dto.AuthorLinksBuilder;
import com.rd2.dto.AuthorLinksDTO;
import com.rd2.dto.BookAuthorsDTO;
import com.rd2.dto.BookDTO;
import com.rd2.dto.BookLinksBuilder;
import com.rd2.dto.BookLinksDTO;
import com.rd2.dto.BookPatchDTO;
import com.rd2.entity.Author;
import com.rd2.entity.Book;
import com.rd2.service.BooksService;
import com.rd2.utils.APIDefaults;
import com.rd2.utils.PagedResponse;
import com.rd2.utils.PaginationLinksBuilder;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/books")
public class BookController extends BasePagedController<Book, BookLinksDTO> {

    // Use @RequiredArgsConstructor to avoid below boilerplate --- IGNORE ---
    BookController(BooksService bookService, 
                   BookLinksBuilder bookLinksBuilder,
                   AuthorLinksBuilder authorLinksBuilder,
                   PaginationLinksBuilder paginationLinksBuilder) {
                    
        super(paginationLinksBuilder);

        this.bookService = bookService;
        this.bookLinksBuilder = bookLinksBuilder;
        this.authorLinksBuilder = authorLinksBuilder;
        //this.paginationLinksBuilder = paginationLinksBuilder;
    }
     
    private final BooksService bookService;
    private final BookLinksBuilder bookLinksBuilder;
    private final AuthorLinksBuilder authorLinksBuilder;
    //private final PaginationLinksBuilder paginationLinksBuilder;

    @Override
    protected BookLinksDTO convertToDTO(Book book) {
        BookLinksDTO dto = new BookLinksDTO(book);
        dto.set_links(bookLinksBuilder.buildResourceLinks(book));
        return dto;
    }
    
    @GetMapping
    public PagedResponse<BookLinksDTO> getAllBooks(
        @RequestParam(defaultValue = APIDefaults.DEFAULT_PAGE_STR) int page,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_SIZE_STR) int size,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_BOOKS_SORT_BY) String sortBy
    ) {
        Pageable pageable = createPageable(page, size, sortBy);
        Page<Book> bookPage = bookService.getAllBooks(pageable);
        
        // Use inherited method - no duplication!
        return buildPagedResponse(bookPage, page, size, bookLinksBuilder);
        /* 
        Pageable pagable = PageRequest.of(page, size, SortBuilder.parseSort(sortBy));
        Page<Book> bookPage = bookService.getAllBooks(pagable);

        List<BookLinksDTO> lstBookLinksDTOs = bookPage.stream()
        .map(book -> {
            BookLinksDTO dto = new BookLinksDTO(book);
            dto.set_links(bookLinksBuilder.buildBookLinks(book));
            return dto;
        })
        .toList();

        List<Link> links = paginationLinksBuilder.buildPaginationLinks(bookPage, page, size, bookLinksBuilder);
        
        PageMetadata pageMetadata = new PageMetadata(
            bookPage.getNumber(),
            bookPage.getSize(),
            bookPage.getTotalElements(),
            bookPage.getTotalPages()
        );

        return new PagedResponse<BookLinksDTO>(
            lstBookLinksDTOs,
            pageMetadata,
            links
        ); */
    }

    @GetMapping("{id}")
    public ResponseEntity<BookLinksDTO> searchBooksById(@PathVariable Integer id) {
        if(id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book book = bookService.searchBooksById(id);
        if(book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookLinksDTO bookLinksDTO = new BookLinksDTO(book);
        bookLinksDTO.set_links(bookLinksBuilder.buildResourceLinks(book));
        
        return new ResponseEntity<BookLinksDTO>(bookLinksDTO, HttpStatus.OK);
    }

    @GetMapping("{id}/authors")
    public ResponseEntity<List<AuthorLinksDTO>> getAuthorsByBookId(@PathVariable Integer id) {
        if(id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Author> authors = bookService.searchAuthorByBookId(id);
        if(authors == null || authors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<AuthorLinksDTO> lstAuthorLinksDTOs = authors.stream()
        .map(author -> {
            AuthorLinksDTO dto = new AuthorLinksDTO(author);
            dto.set_links(authorLinksBuilder.buildResourceLinks(author));
            return dto;
        })
        .toList();
        
        return new ResponseEntity<List<AuthorLinksDTO>>(lstAuthorLinksDTOs, HttpStatus.OK);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookLinksDTO> searchBooksByIsbn(@PathVariable String isbn) {
        if(isbn == null || isbn.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book book = bookService.searchBooksByIsbn(isbn);
        if(book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookLinksDTO bookLinksDTO = new BookLinksDTO(book);
        bookLinksDTO.set_links(bookLinksBuilder.buildResourceLinks(book));
        
        return new ResponseEntity<BookLinksDTO>(bookLinksDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    public PagedResponse<BookLinksDTO> searchBooks(
        @RequestParam String searchTerm,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_PAGE_STR) int page,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_SIZE_STR) int size,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_BOOKS_SORT_BY) String sortBy) {

        Pageable pageable = createPageable(page, size, sortBy);
        Page<Book> bookPage = bookService.searchBooks(searchTerm, pageable);
        
        // Use inherited method - no duplication!
        return buildPagedResponse(bookPage, page, size, bookLinksBuilder);
/* 
        Pageable pagable = PageRequest.of(page, size, SortBuilder.parseSort(sortBy));
        Page<Book> bookPage = bookService.(pagable);

        List<BookLinksDTO> lstBookLinksDTOs = bookPage.stream()
        .map(book -> {
            BookLinksDTO dto = new BookLinksDTO(book);
            dto.set_links(bookLinksBuilder.buildBookLinks(book));
            return dto;
        })
        .toList();

        List<Link> links = paginationLinksBuilder.buildPaginationLinks(bookPage, page, size, bookLinksBuilder);
        
        PageMetadata pageMetadata = new PageMetadata(
            bookPage.getNumber(),
            bookPage.getSize(),
            bookPage.getTotalElements(),
            bookPage.getTotalPages()
        );

        return new PagedResponse<BookLinksDTO>(
            lstBookLinksDTOs,
            pageMetadata,
            links
        ); */
    }

     // Search books by title
    @GetMapping("/title")
    public PagedResponse<BookLinksDTO> searchBooksByTitle(
        @RequestParam String title, 
        @RequestParam(defaultValue = APIDefaults.DEFAULT_PAGE_STR) int page,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_SIZE_STR) int size,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_BOOKS_SORT_BY) String sortBy) {
           
        Pageable pageable = createPageable(page, size, sortBy);
        Page<Book> bookPage = bookService.searchBooksByTitle(title, pageable);

        
        // Use inherited method - no duplication!
        return buildPagedResponse(bookPage, page, size, bookLinksBuilder);
        /* 
        Pageable pagable = PageRequest.of(page, size, SortBuilder.parseSort(sortBy));
        Page<Book> bookPage = bookService.searchBooksByTitle(title, pagable);

        List<BookLinksDTO> lstBookLinksDTOs = bookPage.stream()
        .map(book -> {
            BookLinksDTO dto = new BookLinksDTO(book);
            dto.set_links(bookLinksBuilder.buildBookLinks(book));
            return dto;
        })
        .toList();

        List<Link> links = paginationLinksBuilder.buildPaginationLinks(bookPage, page, size, bookLinksBuilder);
        
        PageMetadata pageMetadata = new PageMetadata(
            bookPage.getNumber(),
            bookPage.getSize(),
            bookPage.getTotalElements(),
            bookPage.getTotalPages()
        );

        return new PagedResponse<BookLinksDTO>(
            lstBookLinksDTOs,
            pageMetadata,
            links
        ); */
    }
    
    // Get books by author
    @GetMapping("/author")
    public PagedResponse<BookLinksDTO> getBooksByAuthor(@RequestParam String name,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_PAGE_STR) int page,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_SIZE_STR) int size,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_BOOKS_SORT_BY) String sortBy) {
        Pageable pageable = createPageable(page, size, sortBy);
        Page<Book> bookPage = bookService.getBooksByAuthor(name, pageable);

        // Use inherited method - no duplication!
        return buildPagedResponse(bookPage, page, size, bookLinksBuilder);
    }
    
    @PostMapping("/create")
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
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) {
        if(id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book book = bookService.searchBooksById(id);
        if(book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book with id " + id + " deleted successfully");
    }
}
