package com.rd2.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel.PageMetadata;

import com.rd2.utils.Link;
import com.rd2.utils.LinksBuilder;
import com.rd2.utils.PagedResponse;
import com.rd2.utils.PaginationLinksBuilder;
import com.rd2.utils.SortBuilder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BasePagedController<T, D> {
    
    protected final PaginationLinksBuilder paginationLinksBuilder;
    
    /**
     * Template method for paginated search.
     * Subclasses only need to implement conversion logic.
     */
    protected PagedResponse<D> buildPagedResponse(
            Page<T> page, 
            int pageNumber, 
            int size,
            LinksBuilder linksBuilder) {
        
        // Convert entities to DTOs
        List<D> dtos = page.getContent().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        
        // Create metadata
        PageMetadata metadata = new PageMetadata(
            page.getSize(),
            page.getNumber(),
            page.getTotalElements(),
            page.getTotalPages()
        );
        
        // Build links
        List<Link> links = paginationLinksBuilder.buildPaginationLinks(
            page, pageNumber, size, linksBuilder
        );
        
        return new PagedResponse<>(dtos, metadata, links);
    }
    
    /**
     * Subclasses must implement DTO conversion.
     */
    protected abstract D convertToDTO(T entity);
    
    /**
     * Helper to create Pageable.
     */
    protected Pageable createPageable(int page, int size, String sortBy) {
        return PageRequest.of(page, size, SortBuilder.parseSort(sortBy));
    }
}

