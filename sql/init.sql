CREATE DATABASE IF NOT EXISTS book_spider_db
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE book_spider_db;

DROP TABLE IF EXISTS book_info;

CREATE TABLE book_info (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  title VARCHAR(512) NOT NULL COMMENT 'Book title',
  category VARCHAR(128) DEFAULT NULL COMMENT 'Category',
  price DECIMAL(10,2) DEFAULT 0.00 COMMENT 'List page price',
  rating INT DEFAULT 0 COMMENT 'Rating 1-5',
  stock VARCHAR(128) DEFAULT NULL COMMENT 'List page stock text',
  image_url VARCHAR(1024) DEFAULT NULL COMMENT 'Cover image URL',
  detail_url VARCHAR(1024) DEFAULT NULL COMMENT 'Original detail page URL',
  upc VARCHAR(128) NOT NULL COMMENT 'Unique product code',
  product_type VARCHAR(128) DEFAULT NULL COMMENT 'Product type',
  price_excl_tax DECIMAL(10,2) DEFAULT 0.00 COMMENT 'Price excluding tax',
  price_incl_tax DECIMAL(10,2) DEFAULT 0.00 COMMENT 'Price including tax',
  tax DECIMAL(10,2) DEFAULT 0.00 COMMENT 'Tax',
  availability VARCHAR(255) DEFAULT NULL COMMENT 'Availability detail',
  reviews INT DEFAULT 0 COMMENT 'Number of reviews',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
  PRIMARY KEY (id),
  UNIQUE KEY uk_book_info_upc (upc),
  KEY idx_book_info_category (category),
  KEY idx_book_info_rating (rating),
  KEY idx_book_info_price (price)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Books to Scrape product data';
