package com.rd2.utils;

public interface LinksBuilder {
    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_SIZE = 10;
    public static final String DEFAULT_PAGE_STR = "0";
    public static final String DEFAULT_SIZE_STR = "10";
    
    Link buildResourceLink(Integer id, String rel, String methodType);

    Link buildCollectionLink();
    
    Link buildPaginationLink(int page, int size, String rel);

    default Link buildSelfLink(Integer id) {
        return buildResourceLink(id, "self", "GET");
    }

    default Link builEditLink(Integer id) {
        return buildResourceLink(id, "edit", "PUT");
    }

    default Link builDeleteLink(Integer id) {
        return buildResourceLink(id, "delete", "DELETE");
    }
    
    default Link buildFirstLink(int size) {
        return buildPaginationLink(0, size, "first");
    }
    
    default Link buildLastLink(int totalPages, int size) {
        return buildPaginationLink(Math.max(0, totalPages - 1), size, "last");
    }
    
    default Link buildNextLink(int currentPage, int size) {
        return buildPaginationLink(currentPage + 1, size, "next");
    }
    
    default Link buildPrevLink(int currentPage, int size) {
        return buildPaginationLink(currentPage - 1, size, "prev");
    }
    
    default Link buildSelfPaginationLink(int page, int size) {
        return buildPaginationLink(page, size, "self");
    }
}
