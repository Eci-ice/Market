import { createStore } from 'vuex';
// import axios from 'axios';

export default createStore({
  state: {
    admin: null,
  },
  getters: {
    isLoggedIn: state => !!state.admin,
    isSeller: state => state.admin && state.admin.power === 1,
    isBuyer: state => state.admin && state.admin.power === 0,
  },
  mutations: {
    setAdmin(state, admin) {
      state.admin = admin;
      // 通常还会设置一个表示已登录的状态，例如：
      state.isLoggedIn = true;
      localStorage.setItem('admin', JSON.stringify(admin));
    },
    clearAdmin(state) {
      state.admin = null;
      localStorage.removeItem('admin');
    }
  },
  actions: {
    login({ commit }, credentials) {
      console.log("Login action triggered", credentials);
      // 定义有效的用户名和密码
      const validSellerUsername = 'seller';
      const validBuyerUsername = 'buyer';
      const validPassword = '123';

      // 检查输入的用户名和密码是否匹配卖家
      if (credentials.username === validSellerUsername && credentials.password === validPassword) {
        // 如果匹配，提交 mutation 更新状态为卖家
        commit('setAdmin', { username: validSellerUsername, power: 1 });
        console.log("Admin set to seller");
      } else if (credentials.username === validBuyerUsername && credentials.password === validPassword) {
        // 检查输入的用户名和密码是否匹配买家
        // 如果匹配，提交 mutation 更新状态为买家
        commit('setAdmin', { username: validBuyerUsername, power: 0 }); // power: 0 代表买家
        console.log("Admin set to buyer");
      } else {
        // 如果不匹配，设置错误或不设置admin
        console.log("Invalid credentials");
        // 可以设置一个错误状态或者不做任何事情
      }
    },
    logout({ commit }) {
      commit('clearAdmin');
    }

    // async login({ commit }, credentials) {
    //   try {
    //     // 实际的 API 调用
    //     const response = await axios.post('/api/login', credentials);
    //     commit('setAdmin', response.data);
    //     return response.data;
    //   } catch (error) {
    //     throw new Error('登录失败');
    //   }
    // },
    // logout({ commit }) {
    //   return new Promise((resolve) => {
    //     commit('clearAdmin');
    //     resolve();
    //   });
    // }
  }
});
