package com.rd2.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rd2.entity.Book;
import com.rd2.utils.AbstractLinksBuilder;
import com.rd2.utils.Link;

@Component
public class BookLinksBuilder extends AbstractLinksBuilder {
    
    private static final String BASE_PATH = "/books";
    
    @Override
    protected String getBasePath() {
        return BASE_PATH;
    }

    public Link buildAuthorsLink(Integer bookId) {
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(BASE_PATH + "/" + bookId + "/authors")
            .toUriString();
        return new Link("authors", uri, "GET");
    }
    
    public Link buildReviewsLink(Integer bookId) {
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(BASE_PATH + "/" + bookId + "/reviews")
            .toUriString();
        return new Link("reviews", uri, "GET");
    }

    public List<Link> buildResourceLinks(Book book) {
        List<Link> links = new ArrayList<>();
        links.add(buildSelfLink(book.getBookId()));
        links.add(builEditLink(book.getBookId()));
        links.add(builDeleteLink(book.getBookId()));
        links.add(buildAuthorsLink(book.getBookId()));
        links.add(buildCollectionLink());
        return links;
    }
}
