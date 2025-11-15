package com.rd2.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.rd2.entity.Book;
import com.rd2.utils.Link;

@lombok.Data
public class BookLinksDTO {
     public BookLinksDTO(Book book) {
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.rating = book.getRating();
        this.description = book.getDescription();
        this.language = book.getLanguage();
        this.isbn = book.getIsbn();
        this.bookFormat = book.getBookFormat();
        this.edition = book.getEdition();
        this.pages = book.getPages();
        this.publisher = book.getPublisher();
        this.publishDate = book.getPublishDate();
        this.firstPublishDate = book.getFirstPublishDate();
        this.likedPercent = book.getLikedPercent();
        this.price = book.getPrice();
    }
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

    private List<Link> _links;
}
