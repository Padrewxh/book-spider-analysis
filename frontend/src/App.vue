<template>
  <el-container class="app-shell">
    <el-aside width="232px" class="sidebar">
      <div class="brand">
        <div class="brand-mark">B</div>
        <div>
          <div class="brand-title">Book Spider</div>
          <div class="brand-subtitle">商品采集分析</div>
        </div>
      </div>
      <el-menu router :default-active="$route.path" class="menu">
        <el-menu-item index="/">
          <el-icon><DataAnalysis /></el-icon>
          <span>数据看板</span>
        </el-menu-item>
        <el-menu-item index="/books">
          <el-icon><Reading /></el-icon>
          <span>图书列表</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="topbar">
        <div>
          <h1>{{ pageTitle }}</h1>
          <p>Books to Scrape 公开练习数据采集结果</p>
        </div>
      </el-header>
      <el-main class="content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { DataAnalysis, Reading } from '@element-plus/icons-vue'

const route = useRoute()
const pageTitle = computed(() => route.meta.title || '图书商品数据分析')
</script>
