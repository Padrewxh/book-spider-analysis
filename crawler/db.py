import pymysql

from config import MYSQL_CONFIG


INSERT_SQL = """
INSERT INTO book_info (
    title, category, price, rating, stock, image_url, detail_url, upc,
    product_type, price_excl_tax, price_incl_tax, tax, availability, reviews
) VALUES (
    %(title)s, %(category)s, %(price)s, %(rating)s, %(stock)s, %(image_url)s,
    %(detail_url)s, %(upc)s, %(product_type)s, %(price_excl_tax)s,
    %(price_incl_tax)s, %(tax)s, %(availability)s, %(reviews)s
) ON DUPLICATE KEY UPDATE
    title = VALUES(title),
    category = VALUES(category),
    price = VALUES(price),
    rating = VALUES(rating),
    stock = VALUES(stock),
    image_url = VALUES(image_url),
    detail_url = VALUES(detail_url),
    product_type = VALUES(product_type),
    price_excl_tax = VALUES(price_excl_tax),
    price_incl_tax = VALUES(price_incl_tax),
    tax = VALUES(tax),
    availability = VALUES(availability),
    reviews = VALUES(reviews),
    update_time = CURRENT_TIMESTAMP
"""


def get_connection():
    return pymysql.connect(**MYSQL_CONFIG)


def save_books(books):
    if not books:
        print("[DB] no data to save")
        return

    connection = get_connection()
    try:
        with connection.cursor() as cursor:
            cursor.executemany(INSERT_SQL, books)
        connection.commit()
        print(f"[DB] saved or updated {len(books)} books")
    except Exception as exc:
        connection.rollback()
        print(f"[DB][ERROR] save failed: {exc}")
        raise
    finally:
        connection.close()
