package com.rd2.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rd2.entity.Author;
import com.rd2.utils.AbstractLinksBuilder;
import com.rd2.utils.Link;

@Component
public class AuthorLinksBuilder extends AbstractLinksBuilder {
    
    private static final String BASE_PATH = "/authors";
    public static final String DEFAULT_SORT_BY = "name asc";

    @Override
    protected String getBasePath() {
        return BASE_PATH;
    }
    
    public Link buildBooksLink(Integer authorId) {
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(BASE_PATH + "/" + authorId + "/books")
            .toUriString();
        return new Link("books", uri, "GET");
    }
    
    public Link buildBiographyLink(Integer authorId) {
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(BASE_PATH + "/" + authorId + "/biography")
            .toUriString();
        return new Link("biography", uri, "GET");
    }

    public List<Link> buildResourceLinks(Author author) {
        List<Link> links = new ArrayList<>();
        links.add(buildSelfLink(author.getAuthorId()));
        links.add(builEditLink(author.getAuthorId()));
        links.add(builDeleteLink(author.getAuthorId()));
        links.add(buildBooksLink(author.getAuthorId()));
        links.add(buildBiographyLink(author.getAuthorId()));

        return links;
    }
}