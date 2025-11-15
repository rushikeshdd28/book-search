package com.rd2.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class AbstractLinksBuilder implements LinksBuilder {

    protected abstract String getBasePath();

    @Override
    public Link buildResourceLink(Integer id, String rel, String methodType) {
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(getBasePath())
            .path("/" + id)
            .toUriString();
        return new Link(rel, uri, methodType);
    }
    
    @Override
    public Link buildCollectionLink() {
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(getBasePath())
            .toUriString();
        return new Link("collection", uri);
    }
    
    @Override
    public Link buildPaginationLink(int page, int size, String rel) {
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(getBasePath())
            .queryParam("page", page)
            .queryParam("size", size)
            .toUriString();
        return new Link(rel, uri);
    }

}
