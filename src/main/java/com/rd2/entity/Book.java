package com.rd2.entity;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String title;
    private BigDecimal rating;
    private String description;
    private String language;
    private String isbn;
    private String book_format;
    private String edition;
    private Integer pages;
    private String publisher;
    private Date publish_date;
    private Date first_publish_date;
    private BigDecimal  liked_percent;
    private BigDecimal  price;
    @Column(name = "search_vector", columnDefinition = "tsvector")
    private String searchVector;

    public Long getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getRating() {
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

    public String getBook_format() {
        return book_format;
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
        return publish_date;
    }

    public Date getFirst_publish_date() {
        return first_publish_date;
    }

    public BigDecimal getLiked_percent() {
        return liked_percent;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSearchVector() {
        return searchVector;
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
                ", book_format='" + book_format + '\'' +
                ", edition='" + edition + '\'' +
                ", pages=" + pages +
                ", publisher='" + publisher + '\'' +
                ", publish_date=" + publish_date +
                ", first_publish_date=" + first_publish_date +
                ", liked_percent=" + liked_percent +
                ", price=" + price +
                '}';
    }

}
