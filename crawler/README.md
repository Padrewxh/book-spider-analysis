# crawler

Python crawler for the public demo site Books to Scrape.

## Run

1. Create MySQL database and table with `../sql/init.sql`.
2. Edit `config.py` and set your MySQL username/password.
3. Install dependencies:

```bash
pip install -r requirements.txt
```

4. Start crawling:

```bash
python spider.py
```

The crawler uses `User-Agent`, sleeps `0.5s` between requests, stores data into MySQL with UPC deduplication, and exports `books.csv`.
