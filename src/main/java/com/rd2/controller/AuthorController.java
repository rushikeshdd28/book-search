package com.rd2.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rd2.dto.AuthorLinksBuilder;
import com.rd2.dto.AuthorLinksDTO;
import com.rd2.entity.Author;
import com.rd2.entity.Book;
import com.rd2.repository.BookRepository;
import com.rd2.service.AuthorService;
import com.rd2.utils.APIDefaults;
import com.rd2.utils.PagedResponse;
import com.rd2.utils.PaginationLinksBuilder;

//import com.rd2.service.BooksService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/authors")
public class AuthorController extends BasePagedController<Author, AuthorLinksDTO> {

    private AuthorService authorService;
    //@Autowired
    //private BooksService bookService;
    private BookRepository bookRepository;

    private AuthorLinksBuilder authorLinksBuilder;

    AuthorController(AuthorService authorService, 
                    BookRepository bookRepository,
                    AuthorLinksBuilder authorLinksBuilder,
                    PaginationLinksBuilder paginationLinksBuilder) {
        super(paginationLinksBuilder);
        this.authorService = authorService;
        this.bookRepository = bookRepository;
        this.authorLinksBuilder = authorLinksBuilder;
    }

    @Override
    protected AuthorLinksDTO convertToDTO(Author author) {
        AuthorLinksDTO dto = new AuthorLinksDTO(author);
        dto.set_links(authorLinksBuilder.buildResourceLinks(author));
        return dto;
    }

    @GetMapping
    public PagedResponse<AuthorLinksDTO> getAllAuthors( 
        @RequestParam(defaultValue = APIDefaults.DEFAULT_PAGE_STR) int page,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_SIZE_STR) int size,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_AUTHOR_SORT_BY) String sortBy
    ) {
        Pageable pageable = createPageable(page, size, sortBy);
        Page<Author> authorPage = authorService.getAllAuthors(pageable);
        
        // Use inherited method - no duplication!
        return buildPagedResponse(authorPage, page, size, authorLinksBuilder);
        //return ResponseEntity.ok(authorService.getAllAuthors());
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
    public PagedResponse<AuthorLinksDTO> searchAuthors(@RequestParam String name, 
        @RequestParam(defaultValue = APIDefaults.DEFAULT_PAGE_STR) int page,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_SIZE_STR) int size,
        @RequestParam(defaultValue = APIDefaults.DEFAULT_AUTHOR_SORT_BY) String sortBy
    ) {
        Pageable pageable = createPageable(page, size, sortBy);
        Page<Author> authorPage = authorService.searchByName(name, pageable);
        
        // Use inherited method - no duplication!
        return buildPagedResponse(authorPage, page, size, authorLinksBuilder);
            //return authorService.searchByName(name, pageable);
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
    
    @PostMapping
    public ResponseEntity<Author> createAuthor(Author author) {
        Author createdAuthor = authorService.createAuthor(author);
        return ResponseEntity.status(201).body(createdAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Integer id, @RequestBody Author author) {
        Author existingAuthor = authorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        // Update fields
        if(author.getName() == null)
        {
            return ResponseEntity.noContent().build();
        }

        existingAuthor.setName(author.getName());
        Author updatedAuthor = authorService.updateAuthor(existingAuthor);
        return ResponseEntity.ok(updatedAuthor);    
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Integer id) {
        Author existingAuthor = authorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        authorService.deleteAuthor(existingAuthor);
        return ResponseEntity.ok("Author with name " + existingAuthor.getName() + " deleted successfully.");
    }
}
