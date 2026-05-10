package com.example.bookspider.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bookspider.entity.BookInfo;

import java.util.List;

public interface BookInfoService extends IService<BookInfo> {
    IPage<BookInfo> pageBooks(long page, long size);

    IPage<BookInfo> searchBooks(String keyword, long page, long size);

    List<BookInfo> listByCategory(String category);

    List<String> listCategories();
}
