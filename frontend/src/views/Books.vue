<template>
  <section class="panel">
    <div class="toolbar">
      <el-input
        v-model="query.keyword"
        clearable
        placeholder="搜索书名、分类或 UPC"
        class="search-input"
        @keyup.enter="loadBooks"
        @clear="loadBooks"
      />
      <el-select v-model="query.category" clearable placeholder="选择分类" class="category-select" @change="handleCategoryChange">
        <el-option v-for="item in categories" :key="item" :label="item" :value="item" />
      </el-select>
      <el-button type="primary" @click="loadBooks">查询</el-button>
      <el-button @click="resetQuery">重置</el-button>
    </div>

    <el-table :data="books" v-loading="loading" border stripe class="book-table">
      <el-table-column prop="title" label="书名" min-width="260" show-overflow-tooltip />
      <el-table-column prop="category" label="分类" width="150" />
      <el-table-column prop="price" label="价格" width="100">
        <template #default="{ row }">£{{ row.price }}</template>
      </el-table-column>
      <el-table-column prop="rating" label="评分" width="110">
        <template #default="{ row }">
          <el-rate :model-value="row.rating" disabled size="small" />
        </template>
      </el-table-column>
      <el-table-column prop="stock" label="库存" width="140" show-overflow-tooltip />
      <el-table-column prop="upc" label="UPC" width="150" show-overflow-tooltip />
      <el-table-column label="操作" width="110" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="showDetail(row)">查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination">
      <el-pagination
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :current-page="query.page"
        :page-size="query.size"
        :page-sizes="[10, 20, 50]"
        @current-change="handlePageChange"
        @size-change="handleSizeChange"
      />
    </div>
  </section>

  <el-dialog v-model="detailVisible" title="图书详情" width="720px">
    <div v-if="currentBook" class="detail">
      <img :src="currentBook.imageUrl" :alt="currentBook.title" class="cover" />
      <div class="detail-info">
        <h2>{{ currentBook.title }}</h2>
        <el-descriptions :column="2" border>
          <el-descriptions-item label="分类">{{ currentBook.category }}</el-descriptions-item>
          <el-descriptions-item label="价格">£{{ currentBook.price }}</el-descriptions-item>
          <el-descriptions-item label="评分">{{ currentBook.rating }}</el-descriptions-item>
          <el-descriptions-item label="库存">{{ currentBook.stock }}</el-descriptions-item>
          <el-descriptions-item label="UPC">{{ currentBook.upc }}</el-descriptions-item>
          <el-descriptions-item label="税前价格">£{{ currentBook.priceExclTax }}</el-descriptions-item>
          <el-descriptions-item label="税后价格">£{{ currentBook.priceInclTax }}</el-descriptions-item>
          <el-descriptions-item label="评论数">{{ currentBook.reviews }}</el-descriptions-item>
        </el-descriptions>
        <el-link :href="currentBook.detailUrl" target="_blank" type="primary">打开原始详情页</el-link>
      </div>
    </div>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getBooks, getBooksByCategory, getCategories, searchBooks } from '../api/book'

const loading = ref(false)
const books = ref([])
const categories = ref([])
const total = ref(0)
const detailVisible = ref(false)
const currentBook = ref(null)

const query = reactive({
  keyword: '',
  category: '',
  page: 1,
  size: 10
})

async function loadBooks() {
  loading.value = true
  try {
    if (query.category) {
      const data = await getBooksByCategory(query.category)
      const filtered = query.keyword
        ? data.filter((item) => `${item.title} ${item.category} ${item.upc}`.toLowerCase().includes(query.keyword.toLowerCase()))
        : data
      total.value = filtered.length
      const start = (query.page - 1) * query.size
      books.value = filtered.slice(start, start + query.size)
      return
    }
    const params = { page: query.page, size: query.size, keyword: query.keyword }
    const data = query.keyword ? await searchBooks(params) : await getBooks(params)
    books.value = data.records || []
    total.value = data.total || 0
  } finally {
    loading.value = false
  }
}

function handleCategoryChange() {
  query.page = 1
  loadBooks()
}

function handlePageChange(page) {
  query.page = page
  loadBooks()
}

function handleSizeChange(size) {
  query.size = size
  query.page = 1
  loadBooks()
}

function resetQuery() {
  query.keyword = ''
  query.category = ''
  query.page = 1
  loadBooks()
}

function showDetail(row) {
  currentBook.value = row
  detailVisible.value = true
}

onMounted(async () => {
  categories.value = await getCategories()
  await loadBooks()
})
</script>
