package com.rd2.utils;

import java.util.List;

import org.springframework.data.web.PagedModel.PageMetadata;

public class PagedResponse<T> {
    private List<T> content;
    private PageMetadata pageMetadata;;
    private List<Link> _links;
    
    public PagedResponse(List<T> content, PageMetadata pageMetadata, List<Link> _links) {
        this.content = content;
        this.pageMetadata = pageMetadata;
        this._links = _links;
    }

    public List<T> getContent() {
        return content;
    }

    public PageMetadata getPageMetadata() {
        return pageMetadata;
    }

    public List<Link> get_links() {
        return _links;
    }
}
