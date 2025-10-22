package com.rd2.dto;

import java.math.BigDecimal;
import java.sql.Date;

@lombok.Data
public class BookAuthorsDTO {
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

    String[] author;
}
