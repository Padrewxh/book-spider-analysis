package com.example.bookspider.service;

import com.example.bookspider.vo.NameValueVO;
import com.example.bookspider.vo.OverviewVO;

import java.util.List;

public interface StatService {
    OverviewVO overview();

    List<NameValueVO> categoryCount();

    List<NameValueVO> ratingCount();

    List<NameValueVO> priceRange();
}
