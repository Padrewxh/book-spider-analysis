<template>
  <div class="dashboard">
    <el-row :gutter="16" class="metrics">
      <el-col :xs="12" :md="6" v-for="item in metricCards" :key="item.label">
        <div class="metric-card">
          <span>{{ item.label }}</span>
          <strong>{{ item.value }}</strong>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="16">
      <el-col :xs="24" :lg="12">
        <section class="panel">
          <div class="panel-title">分类数量统计</div>
          <BaseChart :option="categoryOption" />
        </section>
      </el-col>
      <el-col :xs="24" :lg="12">
        <section class="panel">
          <div class="panel-title">评分分布</div>
          <BaseChart :option="ratingOption" />
        </section>
      </el-col>
      <el-col :xs="24">
        <section class="panel">
          <div class="panel-title">价格区间统计</div>
          <BaseChart :option="priceOption" />
        </section>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import BaseChart from '../components/BaseChart.vue'
import { getCategoryCount, getOverview, getPriceRange, getRatingCount } from '../api/stat'

const overview = ref({})
const categoryCount = ref([])
const ratingCount = ref([])
const priceRange = ref([])

const metricCards = computed(() => [
  { label: '图书总数', value: overview.value.totalBooks ?? 0 },
  { label: '分类总数', value: overview.value.totalCategories ?? 0 },
  { label: '平均价格', value: `£${overview.value.avgPrice ?? 0}` },
  { label: '平均评分', value: overview.value.avgRating ?? 0 }
])

const categoryOption = computed(() => ({
  tooltip: { trigger: 'axis' },
  grid: { left: 42, right: 16, top: 24, bottom: 76 },
  xAxis: {
    type: 'category',
    data: categoryCount.value.map((item) => item.name),
    axisLabel: { rotate: 35, interval: 0 }
  },
  yAxis: { type: 'value' },
  series: [{ type: 'bar', data: categoryCount.value.map((item) => item.value), itemStyle: { color: '#3b82f6' } }]
}))

const ratingOption = computed(() => ({
  tooltip: { trigger: 'item' },
  legend: { bottom: 0 },
  series: [
    {
      type: 'pie',
      radius: ['38%', '66%'],
      data: ratingCount.value.map((item) => ({ name: `${item.name} 星`, value: item.value }))
    }
  ]
}))

const priceOption = computed(() => ({
  tooltip: { trigger: 'axis' },
  grid: { left: 42, right: 16, top: 24, bottom: 36 },
  xAxis: { type: 'category', data: priceRange.value.map((item) => item.name) },
  yAxis: { type: 'value' },
  series: [{ type: 'bar', data: priceRange.value.map((item) => item.value), itemStyle: { color: '#10b981' } }]
}))

onMounted(async () => {
  const [overviewData, categoryData, ratingData, priceData] = await Promise.all([
    getOverview(),
    getCategoryCount(),
    getRatingCount(),
    getPriceRange()
  ])
  overview.value = overviewData || {}
  categoryCount.value = categoryData || []
  ratingCount.value = ratingData || []
  priceRange.value = priceData || []
})
</script>
