import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from '../views/Dashboard.vue'
import Books from '../views/Books.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/', component: Dashboard, meta: { title: '数据看板' } },
    { path: '/books', component: Books, meta: { title: '图书列表' } }
  ]
})

export default router
