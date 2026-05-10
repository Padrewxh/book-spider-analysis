package com.example.bookspider.controller;

import com.example.bookspider.dto.Result;
import com.example.bookspider.service.StatService;
import com.example.bookspider.vo.NameValueVO;
import com.example.bookspider.vo.OverviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stat")
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;

    @GetMapping("/overview")
    public Result<OverviewVO> overview() {
        return Result.success(statService.overview());
    }

    @GetMapping("/category-count")
    public Result<List<NameValueVO>> categoryCount() {
        return Result.success(statService.categoryCount());
    }

    @GetMapping("/rating-count")
    public Result<List<NameValueVO>> ratingCount() {
        return Result.success(statService.ratingCount());
    }

    @GetMapping("/price-range")
    public Result<List<NameValueVO>> priceRange() {
        return Result.success(statService.priceRange());
    }
}
