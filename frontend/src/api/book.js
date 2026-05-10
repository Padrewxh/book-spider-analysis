import request from './request'

export function getBooks(params) {
  return request.get('/books', { params })
}

export function searchBooks(params) {
  return request.get('/books/search', { params })
}

export function getBooksByCategory(category) {
  return request.get(`/books/category/${encodeURIComponent(category)}`)
}

export function getCategories() {
  return request.get('/books/categories')
}
