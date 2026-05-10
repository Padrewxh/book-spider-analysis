package com.example.bookspider.service.impl;

import com.example.bookspider.mapper.BookInfoMapper;
import com.example.bookspider.service.StatService;
import com.example.bookspider.vo.NameValueVO;
import com.example.bookspider.vo.OverviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final BookInfoMapper bookInfoMapper;

    @Override
    public OverviewVO overview() {
        return bookInfoMapper.selectOverview();
    }

    @Override
    public List<NameValueVO> categoryCount() {
        return bookInfoMapper.selectCategoryCount();
    }

    @Override
    public List<NameValueVO> ratingCount() {
        return bookInfoMapper.selectRatingCount();
    }

    @Override
    public List<NameValueVO> priceRange() {
        return bookInfoMapper.selectPriceRange();
    }
}
