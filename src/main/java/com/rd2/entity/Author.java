package com.rd2.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorId;  
    private String name;

    public Integer getAuthorId() {
        return authorId;
    }
   
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();
    
    public Set<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Author [authorId=" + authorId + ", name=" + name + "]";
    }

}
