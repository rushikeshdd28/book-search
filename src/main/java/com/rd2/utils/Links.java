package com.rd2.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // Don't include null fields
public class Links {
    private Link self;
    private Link first;
    private Link last;
    private Link next;   // Optional - only if has next page
    private Link prev;   // Optional - only if has previous page
    
    private Link update;
    private Link delete;
    private Link authors;
    private Link books;

    
    // Constructor
    public Links() {
    }
    
    public Link getUpdate() {
        return update;
    }

    public void setUpdate(Link update) {
        this.update = update;
    }

    public Link getDelete() {
        return delete;
    }

    public void setDelete(Link delete) {
        this.delete = delete;
    }

    public Link getAuthors() {
        return authors;
    }

    public void setAuthors(Link authors) {
        this.authors = authors;
    }

    public Link getBooks() {
        return books;
    }

    public void setBooks(Link books) {
        this.books = books;
    }
    
    // Getters and Setters (you need both for this class)
    public Link getSelf() {
        return self;
    }
    
    public void setSelf(Link self) {
        this.self = self;
    }
    
    public Link getFirst() {
        return first;
    }
    
    public void setFirst(Link first) {
        this.first = first;
    }
    
    public Link getLast() {
        return last;
    }
    
    public void setLast(Link last) {
        this.last = last;
    }
    
    public Link getNext() {
        return next;
    }
    
    public void setNext(Link next) {
        this.next = next;
    }
    
    public Link getPrev() {
        return prev;
    }
    
    public void setPrev(Link prev) {
        this.prev = prev;
    }
}