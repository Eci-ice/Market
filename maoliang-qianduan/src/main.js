import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';

const app = createApp(App);

// 检查本地存储并初始化store的state
const adminData = localStorage.getItem('admin');
if (adminData) {
  store.commit('setAdmin', JSON.parse(adminData));
}

app.use(router);
app.use(store);
app.mount('#app');
