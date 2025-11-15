package com.rd2.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PaginationLinksBuilder {

        public List<Link> buildPaginationLinks(Page<?> page, int currentPage, int size, 
                                           LinksBuilder builder) {
        List<Link> links = new ArrayList<>();
        
        // Self link
        links.add(builder.buildSelfPaginationLink(currentPage, size));
        
        // First and last links (always present)
        links.add(builder.buildFirstLink(size));
        links.add(builder.buildLastLink(page.getTotalPages(), size));
        
        // Next link (if not on last page)
        if (page.hasNext()) {
            links.add(builder.buildNextLink(currentPage, size));
        }
        
        // Previous link (if not on first page)
        if (page.hasPrevious()) {
            links.add(builder.buildPrevLink(currentPage, size));
        }
        
        return links;
    }

}
