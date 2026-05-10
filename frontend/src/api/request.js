import axios from 'axios'
import { ElMessage } from 'element-plus'
import { API_BASE_URL } from './config'

const request = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000
})

request.interceptors.response.use(
  (response) => {
    const result = response.data
    if (result.code !== 200) {
      ElMessage.error(result.message || '请求失败')
      return Promise.reject(new Error(result.message || '请求失败'))
    }
    return result.data
  },
  (error) => {
    ElMessage.error(error.message || '网络异常')
    return Promise.reject(error)
  }
)

export default request
