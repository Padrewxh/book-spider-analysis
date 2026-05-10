package com.example.bookspider.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("book_info")
public class BookInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String category;
    private BigDecimal price;
    private Integer rating;
    private String stock;
    private String imageUrl;
    private String detailUrl;
    private String upc;
    private String productType;
    private BigDecimal priceExclTax;
    private BigDecimal priceInclTax;
    private BigDecimal tax;
    private String availability;
    private Integer reviews;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
