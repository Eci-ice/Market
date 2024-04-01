import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import axios from "axios";

const app = createApp(App);

// 检查本地存储并初始化store的state
const adminData = localStorage.getItem('admin');
if (adminData) {
  store.commit('setAdmin', JSON.parse(adminData));
}

// 设置基本的 URL
axios.defaults.baseURL = process.env.NODE_ENV === 'development' ? '/' : 'http://localhost:8080/';

// 添加 Axios 请求拦截器
axios.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  // 判断请求目标地址是否包含/api，如果不包含则添加/api前缀
  if (!config.url.includes('/api')) {
    config.url = `/api${config.url}`;
  }
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

// 添加全局 Fetch 请求拦截器
const originalFetch = window.fetch;
window.fetch = function (url, options) {
  // 判断请求目标地址是否包含/api，如果不包含则添加/api前缀
  const apiUrl = !url.includes('/api') ? `/api${url}` : url;
  return originalFetch(apiUrl, options);
};

app.use(router);
app.use(store);
app.mount('#app');
