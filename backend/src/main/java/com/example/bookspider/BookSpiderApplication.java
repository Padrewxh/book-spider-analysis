package com.example.bookspider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.bookspider.mapper")
public class BookSpiderApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookSpiderApplication.class, args);
    }
}
