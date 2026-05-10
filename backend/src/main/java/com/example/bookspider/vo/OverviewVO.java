package com.example.bookspider.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OverviewVO {
    private Long totalBooks;
    private Long totalCategories;
    private BigDecimal avgPrice;
    private BigDecimal avgRating;
    private BigDecimal maxPrice;
    private BigDecimal minPrice;
}
