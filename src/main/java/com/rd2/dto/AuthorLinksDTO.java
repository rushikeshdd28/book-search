package com.rd2.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rd2.entity.Author;
import com.rd2.entity.Book;
import com.rd2.utils.Link;

import jakarta.persistence.ManyToMany;

public class AuthorLinksDTO {
    private Integer authorId;  
    private String name;
    List<Link> _links;

    public AuthorLinksDTO(Author author) {
        this.authorId = author.getAuthorId();
        this.name = author.getName();
    }

    public Integer getAuthorId() {
        return authorId;
    }
   
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Link> get_links() {
        return _links;
    }

    public void set_links(List<Link> _links) {
        this._links = _links;
    }

    public Set<Book> getBooks() {
        return books;
    }

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();
}
