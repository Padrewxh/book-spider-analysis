package com.example.bookspider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bookspider.entity.BookInfo;
import com.example.bookspider.vo.NameValueVO;
import com.example.bookspider.vo.OverviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookInfoMapper extends BaseMapper<BookInfo> {
    @Select("""
            SELECT COUNT(*) AS totalBooks,
                   COUNT(DISTINCT category) AS totalCategories,
                   ROUND(IFNULL(AVG(price), 0), 2) AS avgPrice,
                   ROUND(IFNULL(AVG(rating), 0), 2) AS avgRating,
                   IFNULL(MAX(price), 0) AS maxPrice,
                   IFNULL(MIN(price), 0) AS minPrice
            FROM book_info
            """)
    OverviewVO selectOverview();

    @Select("""
            SELECT category AS name, COUNT(*) AS value
            FROM book_info
            GROUP BY category
            ORDER BY value DESC, category ASC
            """)
    List<NameValueVO> selectCategoryCount();

    @Select("""
            SELECT rating AS name, COUNT(*) AS value
            FROM book_info
            GROUP BY rating
            ORDER BY rating ASC
            """)
    List<NameValueVO> selectRatingCount();

    @Select("""
            SELECT r.name, COUNT(t.range_name) AS value
            FROM (
                SELECT '0-10' AS name, 1 AS sort_no
                UNION ALL SELECT '10-20', 2
                UNION ALL SELECT '20-30', 3
                UNION ALL SELECT '30-40', 4
                UNION ALL SELECT '40-50', 5
                UNION ALL SELECT '50+', 6
            ) r
            LEFT JOIN (
                SELECT CASE
                    WHEN price < 10 THEN '0-10'
                    WHEN price < 20 THEN '10-20'
                    WHEN price < 30 THEN '20-30'
                    WHEN price < 40 THEN '30-40'
                    WHEN price < 50 THEN '40-50'
                    ELSE '50+'
                END AS range_name,
                CASE
                    WHEN price < 10 THEN 1
                    WHEN price < 20 THEN 2
                    WHEN price < 30 THEN 3
                    WHEN price < 40 THEN 4
                    WHEN price < 50 THEN 5
                    ELSE 6
                END AS range_sort
                FROM book_info
            ) t ON r.name = t.range_name
            GROUP BY r.name, r.sort_no
            ORDER BY r.sort_no
            """)
    List<NameValueVO> selectPriceRange();
}
