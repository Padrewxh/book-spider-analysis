import re


RATING_MAP = {
    "One": 1,
    "Two": 2,
    "Three": 3,
    "Four": 4,
    "Five": 5,
}


def clean_text(value):
    if value is None:
        return ""
    return re.sub(r"\s+", " ", str(value)).strip()


def clean_price(value):
    text = clean_text(value).replace("£", "").replace(",", "")
    if not text:
        return 0.0
    return float(text)


def clean_rating(value):
    value = clean_text(value)
    return RATING_MAP.get(value, 0)


def clean_int(value):
    text = clean_text(value)
    match = re.search(r"\d+", text)
    return int(match.group()) if match else 0


def clean_book(book):
    return {
        "title": clean_text(book.get("title")),
        "category": clean_text(book.get("category")),
        "price": clean_price(book.get("price")),
        "rating": clean_rating(book.get("rating")),
        "stock": clean_text(book.get("stock")),
        "image_url": clean_text(book.get("image_url")),
        "detail_url": clean_text(book.get("detail_url")),
        "upc": clean_text(book.get("upc")),
        "product_type": clean_text(book.get("product_type")),
        "price_excl_tax": clean_price(book.get("price_excl_tax")),
        "price_incl_tax": clean_price(book.get("price_incl_tax")),
        "tax": clean_price(book.get("tax")),
        "availability": clean_text(book.get("availability")),
        "reviews": clean_int(book.get("reviews")),
    }
