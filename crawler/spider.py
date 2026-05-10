import time
from urllib.parse import urljoin

import pandas as pd
import requests
from bs4 import BeautifulSoup
from tqdm import tqdm

from clean_data import clean_book
from config import BASE_URL, CSV_OUTPUT, HEADERS, REQUEST_DELAY, START_URL, TIMEOUT
from db import save_books


class BookSpider:
    def __init__(self):
        self.session = requests.Session()
        self.session.headers.update(HEADERS)

    def fetch(self, url):
        time.sleep(REQUEST_DELAY)
        try:
            response = self.session.get(url, timeout=TIMEOUT)
            response.raise_for_status()
            response.encoding = "utf-8"
            return response.text
        except requests.RequestException as exc:
            print(f"[HTTP][ERROR] {url}: {exc}")
            return None

    def parse_list_page(self, url):
        html = self.fetch(url)
        if not html:
            return [], None

        soup = BeautifulSoup(html, "html.parser")
        books = []

        for item in soup.select("article.product_pod"):
            try:
                title_node = item.select_one("h3 a")
                price_node = item.select_one(".price_color")
                stock_node = item.select_one(".availability")
                rating_node = item.select_one("p.star-rating")
                image_node = item.select_one("img")
                relative_detail = title_node.get("href")
                detail_url = urljoin(url, relative_detail)

                book = {
                    "title": title_node.get("title"),
                    "category": "",
                    "price": price_node.get_text(strip=True),
                    "rating": self.extract_rating(rating_node),
                    "stock": stock_node.get_text(" ", strip=True),
                    "image_url": urljoin(url, image_node.get("src")),
                    "detail_url": detail_url,
                }
                book.update(self.parse_detail_page(detail_url))
                books.append(clean_book(book))
            except Exception as exc:
                print(f"[PARSE][ERROR] list item on {url}: {exc}")

        next_node = soup.select_one("li.next a")
        next_url = urljoin(url, next_node.get("href")) if next_node else None
        return books, next_url

    def parse_category(self, soup):
        breadcrumb = soup.select("ul.breadcrumb li a")
        if len(breadcrumb) >= 3:
            return breadcrumb[-1].get_text(strip=True)
        current = soup.select_one("ul.breadcrumb li.active")
        return current.get_text(strip=True) if current else "Books"

    def extract_rating(self, rating_node):
        if not rating_node:
            return ""
        classes = rating_node.get("class", [])
        for cls in classes:
            if cls != "star-rating":
                return cls
        return ""

    def parse_detail_page(self, url):
        html = self.fetch(url)
        if not html:
            return {}

        soup = BeautifulSoup(html, "html.parser")
        detail = {}
        for row in soup.select("table.table.table-striped tr"):
            key = row.select_one("th").get_text(strip=True)
            value = row.select_one("td").get_text(strip=True)
            detail[key] = value

        return {
            "category": self.parse_category(soup),
            "upc": detail.get("UPC", ""),
            "product_type": detail.get("Product Type", ""),
            "price_excl_tax": detail.get("Price (excl. tax)", ""),
            "price_incl_tax": detail.get("Price (incl. tax)", ""),
            "tax": detail.get("Tax", ""),
            "availability": detail.get("Availability", ""),
            "reviews": detail.get("Number of reviews", "0"),
        }

    def crawl_all(self):
        url = START_URL
        all_books = []
        page_no = 1

        while url:
            print(f"[SPIDER] crawling page {page_no}: {url}")
            books, url = self.parse_list_page(url)
            all_books.extend(books)
            page_no += 1

        return all_books


def main():
    spider = BookSpider()
    books = spider.crawl_all()
    if not books:
        print("[SPIDER] no books crawled")
        return

    for _ in tqdm(books, desc="Preparing data"):
        pass

    pd.DataFrame(books).to_csv(CSV_OUTPUT, index=False, encoding="utf-8-sig")
    print(f"[CSV] saved {len(books)} rows to {CSV_OUTPUT}")
    save_books(books)


if __name__ == "__main__":
    main()
