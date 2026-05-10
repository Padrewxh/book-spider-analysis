package com.example.bookspider.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.bookspider.dto.Result;
import com.example.bookspider.entity.BookInfo;
import com.example.bookspider.service.BookInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookInfoService bookInfoService;

    @GetMapping
    public Result<IPage<BookInfo>> pageBooks(
            @RequestParam(value = "page", defaultValue = "1") long page,
            @RequestParam(value = "size", defaultValue = "10") long size) {
        return Result.success(bookInfoService.pageBooks(page, size));
    }

    @GetMapping("/search")
    public Result<IPage<BookInfo>> searchBooks(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "1") long page,
            @RequestParam(value = "size", defaultValue = "10") long size) {
        return Result.success(bookInfoService.searchBooks(keyword, page, size));
    }

    @GetMapping("/category/{category}")
    public Result<List<BookInfo>> listByCategory(@PathVariable("category") String category) {
        return Result.success(bookInfoService.listByCategory(category));
    }

    @GetMapping("/categories")
    public Result<List<String>> listCategories() {
        return Result.success(bookInfoService.listCategories());
    }
}
