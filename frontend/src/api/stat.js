import request from './request'

export function getOverview() {
  return request.get('/stat/overview')
}

export function getCategoryCount() {
  return request.get('/stat/category-count')
}

export function getRatingCount() {
  return request.get('/stat/rating-count')
}

export function getPriceRange() {
  return request.get('/stat/price-range')
}
