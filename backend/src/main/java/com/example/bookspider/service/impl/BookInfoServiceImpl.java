package com.example.bookspider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bookspider.entity.BookInfo;
import com.example.bookspider.mapper.BookInfoMapper;
import com.example.bookspider.service.BookInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, BookInfo> implements BookInfoService {
    @Override
    public IPage<BookInfo> pageBooks(long page, long size) {
        LambdaQueryWrapper<BookInfo> wrapper = new LambdaQueryWrapper<BookInfo>()
                .orderByDesc(BookInfo::getCreateTime)
                .orderByDesc(BookInfo::getId);
        return page(new Page<>(page, size), wrapper);
    }

    @Override
    public IPage<BookInfo> searchBooks(String keyword, long page, long size) {
        LambdaQueryWrapper<BookInfo> wrapper = new LambdaQueryWrapper<BookInfo>()
                .and(StringUtils.hasText(keyword), q -> q
                        .like(BookInfo::getTitle, keyword)
                        .or()
                        .like(BookInfo::getUpc, keyword)
                        .or()
                        .like(BookInfo::getCategory, keyword))
                .orderByDesc(BookInfo::getCreateTime)
                .orderByDesc(BookInfo::getId);
        return page(new Page<>(page, size), wrapper);
    }

    @Override
    public List<BookInfo> listByCategory(String category) {
        return list(new LambdaQueryWrapper<BookInfo>()
                .eq(BookInfo::getCategory, category)
                .orderByAsc(BookInfo::getTitle));
    }

    @Override
    public List<String> listCategories() {
        return listObjs(new LambdaQueryWrapper<BookInfo>()
                .select(BookInfo::getCategory)
                .isNotNull(BookInfo::getCategory)
                .groupBy(BookInfo::getCategory)
                .orderByAsc(BookInfo::getCategory), Object::toString);
    }
}
