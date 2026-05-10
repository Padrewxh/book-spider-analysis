BASE_URL = "https://books.toscrape.com/"
START_URL = "https://books.toscrape.com/index.html"

REQUEST_DELAY = 0.5
TIMEOUT = 15

HEADERS = {
    "User-Agent": (
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
        "AppleWebKit/537.36 (KHTML, like Gecko) "
        "Chrome/124.0 Safari/537.36 BookSpiderLearning/1.0"
    )
}

MYSQL_CONFIG = {
    "host": "localhost",
    "port": 3306,
    "user": "root",
    "password": "123456",
    "database": "book_spider_db",
    "charset": "utf8mb4",
}

CSV_OUTPUT = "books.csv"
