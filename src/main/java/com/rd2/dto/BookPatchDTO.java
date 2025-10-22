package com.rd2.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

@Data
public class BookPatchDTO {
    
    private Optional<String> title = Optional.empty();
;
    
    //@Min(value = 0, message = "Rating must be between 0 and 5")
    //@Max(value = 5, message = "Rating must be between 0 and 5")
    private Optional<Double> rating = Optional.empty();
    
    private Optional<String> description = Optional.empty();
    
    private Optional<String> language = Optional.empty();
    
    private Optional<String> isbn = Optional.empty();
    
    private Optional<String> bookFormat = Optional.empty();
    
    private Optional<String> edition = Optional.empty();
    
    //@Min(value = 1, message = "Pages must be positive")
    private Optional<Integer> pages = Optional.empty();
    
    private Optional<String> publisher = Optional.empty();
    
    private Optional<Date> publishDate = Optional.empty();
    
    private Optional<Date> firstPublishDate = Optional.empty();
    
    //@Min(value = 0, message = "Liked percent must be between 0 and 100")
    //@Max(value = 100, message = "Liked percent must be between 0 and 100")
    private Optional<Double> likedPercent = Optional.empty();
    
    //@DecimalMin(value = "0.0", message = "Price must be positive")
    private Optional<BigDecimal> price = Optional.empty();
    
    // Note: Excluding bookId, searchVector (auto-managed), authors (separate endpoint)
}
