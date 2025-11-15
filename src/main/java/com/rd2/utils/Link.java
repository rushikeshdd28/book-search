package com.rd2.utils;

public class Link {
    private String rel;
    private String href;
    private String methodType;
    
    // Constructor
    public Link(String rel, String href) {
        this(rel, href, "GET");
    }

    public Link(String rel, String href, String methodType) {
        this.rel = rel;
        this.href = href;
        this.methodType = methodType;
    }
    
    public String getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public void setHref(String href) {
        this.href = href;
    }
    
    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }
}
