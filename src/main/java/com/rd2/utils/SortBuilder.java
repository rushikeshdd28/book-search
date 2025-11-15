package com.rd2.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;

public class SortBuilder {

    public static Sort parseSort(String sortString) {
        List<Sort.Order> orders = new ArrayList<>();
        String[] sortParams = sortString.split(",");
        for (String param : sortParams) {
            String[] parts = param.trim().split(" ");
            String property = parts[0];
            String direction = "";
            if(parts.length > 1 && parts[1] != null && !parts[1].isEmpty()) {
                direction = parts[1].toLowerCase();
            }
            if (direction.equals("desc")) {
                orders.add(Sort.Order.desc(property));
            } 
            else 
            {
                orders.add(Sort.Order.asc(property));
            } 
        }    
        return Sort.by(orders);
    }
}
