package com.rd2.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    private String title;
    private Double rating;
    private String description;
    private String language;
    private String isbn;
    private String bookFormat;
    private String edition;
    private Integer pages;
    private String publisher;
    private Date publishDate;
    private Date firstPublishDate;
    private Double likedPercent;
    private BigDecimal price;
    @Column(name = "search_vector", columnDefinition = "tsvector", insertable = false, updatable = false)
    private String searchVector;

    public Integer getBookId() {
        return bookId;
    }

    public Double getLikedPercent() {
        return likedPercent;
    }

    public void setLikedPercent(Double likedPercent) {
        this.likedPercent = likedPercent;
    }

    public void setBookFormat(String bookFormat) {
        this.bookFormat = bookFormat;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getFirstPublishDate() {
        return firstPublishDate;
    }

    public void setFirstPublishDate(Date firstPublishDate) {
        this.firstPublishDate = firstPublishDate;
    }

    public String getTitle() {
        return title;
    }

    public Double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getBookFormat() {
        return bookFormat;
    }

    public String getEdition() {
        return edition;
    }

    public Integer getPages() {
        return pages;
    }

    public String getPublisher() {
        return publisher;
    }

    public Date getPublish_date() {
        return publishDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSearchVector() {
        return searchVector;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "books_authors",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();
    
    // Helper methods for bidirectional relationship
    public void addAuthor(Author author) {
        this.authors.add(author);
        author.getBooks().add(this);
    }
    
    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", language='" + language + '\'' +
                ", isbn='" + isbn + '\'' +
                ", book_format='" + bookFormat + '\'' +
                ", edition='" + edition + '\'' +
                ", pages=" + pages +
                ", publisher='" + publisher + '\'' +
                ", publish_date=" + publishDate +
                ", first_publish_date=" + firstPublishDate +
                ", liked_percent=" + likedPercent +
                ", price=" + price +
                '}';
    }

}
